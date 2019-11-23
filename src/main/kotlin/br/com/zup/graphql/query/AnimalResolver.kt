package br.com.zup.graphql.query

import br.com.zup.data.Animal
import br.com.zup.data.impl.Bulldog
import br.com.zup.data.impl.Pitbull
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class AnimalResolver: GraphQLQueryResolver {

    fun getAnimalByName(name: String) : Animal? {
        if(name.equals("hank", true)){
            return Pitbull("hank", "vermelho")
        }else if(name.equals("josh", true)){
            return Bulldog("josh", 30)
        }
        return null
    }

    fun getAnimals() : List<Animal> {
        val animalList = ArrayList<Animal>()
        animalList.add(Pitbull("hank", "vermelho"))
        animalList.add(Bulldog("josh", 30))
        return animalList
    }
}