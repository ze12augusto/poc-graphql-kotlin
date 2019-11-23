package br.com.zup.configuration

import br.com.zup.graphql.query.AnimalQuery
import br.com.zup.graphql.query.PersonQuery
import com.coxautodev.graphql.tools.SchemaParserDictionary
import graphql.TypeResolutionEnvironment
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import graphql.schema.StaticDataFetcher
import graphql.schema.TypeResolver
import graphql.schema.idl.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.core.type.filter.AnnotationTypeFilter
import java.io.File


@Configuration
open class GraphQLConfiguration(private val resourcePatternResolver: ResourcePatternResolver,
                                private val animalQuery: AnimalQuery,
                                private val personQuery: PersonQuery) {

    @Bean
    open fun schema(): GraphQLSchema? {
        val schemaParser = SchemaParser()
        val schemaGenerator = SchemaGenerator()
        val schemaFiles = getAllGraphQLFiles("classpath:graphql/*.graphqls")
        val typeRegistry: TypeDefinitionRegistry = TypeDefinitionRegistry()
        val wiring: RuntimeWiring = buildRuntimeWiring()
        for(schemaFile in schemaFiles){
            typeRegistry.merge(schemaParser.parse(schemaFile))
        }
        return schemaGenerator.makeExecutableSchema(typeRegistry, wiring)
    }

    @Bean
    open fun dictionary(): SchemaParserDictionary {
        val genericInterfaceImplList = findAnnotatedClassesWithGenericInterfaceImplAnnotation("br.com.zup.data.impl")
        var schemaParserDictionary = SchemaParserDictionary()
        for(clazz in genericInterfaceImplList){
            schemaParserDictionary.add(Class.forName(clazz))
        }
        return schemaParserDictionary
    }

    private fun getAllGraphQLFiles(path: String): List<File> {
        val list = ArrayList<File>()
        for(resource in this.resourcePatternResolver.getResources(path)){
            list.add(resource.file)
        }
        return list
    }

    private fun buildRuntimeWiring(): RuntimeWiring {
        val genericInterfaces: List<String>  = findAnnotatedClassesWithGenericInterfaceAnnotation("br.com.zup.data")
        val typeResolver = CustomTypeResolver()
        var runtimeWiringBuilder: RuntimeWiring.Builder = RuntimeWiring.newRuntimeWiring()
        for(genericInterface in genericInterfaces){
            runtimeWiringBuilder.type(
                TypeRuntimeWiring.newTypeWiring(genericInterface)
                    .typeResolver(typeResolver)
            )
        }
        return runtimeWiringBuilder.type("QueryType") { typeWiring: TypeRuntimeWiring.Builder ->
                            typeWiring.dataFetcher("animalByName", StaticDataFetcher(this.animalQuery.getAnimalByName()))
                typeWiring.dataFetcher("personByName", StaticDataFetcher(this.personQuery.getPersonByName()))
        }
            .build()
    }

    private fun findAnnotatedClassesWithGenericInterfaceAnnotation(scanPackage: String): List<String> {
        val provider = createComponentScannerWithGenericInterfaceAnnotation()
        val list = ArrayList<String>()
        for (bean in provider.findCandidateComponents(scanPackage)) {
            list.add(bean.beanClassName!!)
        }
        return list
    }

    private fun createComponentScannerWithGenericInterfaceAnnotation(): ClassPathScanningCandidateComponentProvider {
        val provider = ClassPathScanningCandidateComponentProvider(false)
        provider.addIncludeFilter(AnnotationTypeFilter(GenericInterface::class.java))
        return provider
    }

    private fun findAnnotatedClassesWithGenericInterfaceImplAnnotation(scanPackage: String): List<String> {
        val provider = createComponentScannerWithGenericInterfaceImplAnnotation()
        val list = ArrayList<String>()
        for (bean in provider.findCandidateComponents(scanPackage)) {
            list.add(bean.beanClassName!!)
        }
        return list
    }

    private fun createComponentScannerWithGenericInterfaceImplAnnotation(): ClassPathScanningCandidateComponentProvider {
        val provider = ClassPathScanningCandidateComponentProvider(false)
        provider.addIncludeFilter(AnnotationTypeFilter(GenericInterfaceImpl::class.java))
        return provider
    }

    class CustomTypeResolver: TypeResolver {

        override fun getType(value: TypeResolutionEnvironment?): GraphQLObjectType {
            var type: String = value!!.getObject<String>()::class.simpleName!!
            return value.schema.getObjectType(type)
        }

    }

}