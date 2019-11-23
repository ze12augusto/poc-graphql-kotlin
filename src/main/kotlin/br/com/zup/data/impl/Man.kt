package br.com.zup.data.impl

import br.com.zup.configuration.GenericInterfaceImpl
import br.com.zup.data.Person

@GenericInterfaceImpl
class Man(name: String, val sport: String) : Person(name) {
}