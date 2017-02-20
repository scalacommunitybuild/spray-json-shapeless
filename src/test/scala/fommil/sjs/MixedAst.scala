package fommil.sjs

import shapeless.record._

object MixedAst {
  type Address = Record.`'city -> String, 'street -> String, 'house -> String`.T
  case class Person(name: String, address: Address)
  type Department = Record.`'name -> String, 'employees -> Vector[Person]`.T
}
