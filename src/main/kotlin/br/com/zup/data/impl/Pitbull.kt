package br.com.zup.data.impl

import br.com.zup.configuration.GenericInterfaceImpl
import br.com.zup.data.Animal

@GenericInterfaceImpl
class Pitbull(name: String, val color: String) : Animal(name) {
}