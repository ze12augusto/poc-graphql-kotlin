package br.com.zup.data.impl

import br.com.zup.configuration.GraphQLType
import br.com.zup.data.Dog

@GraphQLType
class Pitbull(val name: String, val color: String) : Dog {
}