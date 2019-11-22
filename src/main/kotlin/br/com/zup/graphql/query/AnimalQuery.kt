package br.com.zup.graphql.query

import br.com.zup.data.Animal
import br.com.zup.data.impl.Pitbull
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class AnimalQuery: GraphQLQueryResolver {

    fun getAnimalByName() : Animal {
        return Pitbull("hank", "vermelho")
    }

    fun getPitbull(): Pitbull {
        return Pitbull("hank", "amarelo")
    }
}