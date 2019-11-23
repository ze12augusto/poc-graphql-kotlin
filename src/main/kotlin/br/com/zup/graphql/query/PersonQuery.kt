package br.com.zup.graphql.query

import br.com.zup.data.Person
import br.com.zup.data.impl.Man
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class PersonQuery: GraphQLQueryResolver {

    fun getPersonByName() : Person {
        return Man("john", "baseball")
    }
}