package br.com.zup.data.impl

import br.com.zup.configuration.GenericInterfaceImpl
import br.com.zup.data.Person

@GenericInterfaceImpl
class Woman(name: String, val hairStyle: String) : Person(name) {
}