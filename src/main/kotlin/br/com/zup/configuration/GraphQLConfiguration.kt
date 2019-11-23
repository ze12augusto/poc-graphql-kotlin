package br.com.zup.configuration

import com.coxautodev.graphql.tools.SchemaParserDictionary
import org.reflections.Reflections
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.full.createInstance


@Configuration
open class GraphQLConfiguration {

    @Bean
    open fun dictionary(): SchemaParserDictionary {
        val genericInterfaceImplList = findAnnotatedClasses("br.com.zup", GraphQLType::class.createInstance())
        val schemaParserDictionary = SchemaParserDictionary()
        genericInterfaceImplList.forEach{
            schemaParserDictionary.add(it)
        }
        return schemaParserDictionary
    }

    private fun findAnnotatedClasses(scanPackage: String, annotation: Annotation): List<Class<*>> {
        val reflections = Reflections(scanPackage)
        val list = ArrayList<Class<*>>()
        reflections.getTypesAnnotatedWith(annotation).forEach{
            list.add(it)
        }
        return list
    }

}