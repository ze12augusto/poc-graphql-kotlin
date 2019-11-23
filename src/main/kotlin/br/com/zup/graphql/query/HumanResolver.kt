package br.com.zup.graphql.query

import br.com.zup.data.Animal
import br.com.zup.data.impl.Man
import br.com.zup.data.impl.Woman
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class HumanResolver: GraphQLQueryResolver {

    fun getHumanByName(name: String) : Animal? {
        if(name.equals("john", true)){
            return Man("john", "baseball")
        }else if(name.equals("josh", true)){
            return Woman("mary", "long")
        }
        return null
    }

    fun getHumans() : List<Animal> {
        val humanList = ArrayList<Animal>()
        humanList.add(Man("john", "baseball"))
        humanList.add(Woman("mary", "long"))
        return humanList
    }
}