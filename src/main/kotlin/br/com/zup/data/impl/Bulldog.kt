package br.com.zup.data.impl

import br.com.zup.configuration.GraphQLType
import br.com.zup.data.Dog

@GraphQLType
class Bulldog(val name: String, val weight: Int) : Dog {
}