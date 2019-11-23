package br.com.zup.data.impl

import br.com.zup.configuration.GenericInterfaceImpl
import br.com.zup.data.Animal

@GenericInterfaceImpl
class Bulldog(name: String, val weight: Int) : Animal(name) {
}