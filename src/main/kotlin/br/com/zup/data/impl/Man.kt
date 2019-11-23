package br.com.zup.data.impl

import br.com.zup.configuration.GraphQLType
import br.com.zup.data.Human

@GraphQLType
class Man(val name: String, val sport: String) : Human{
}