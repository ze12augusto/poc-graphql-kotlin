package br.com.zup.data.impl

import br.com.zup.configuration.GraphQLType
import br.com.zup.data.Human

@GraphQLType
class Woman(val name: String, val hairStyle: String) : Human {
}