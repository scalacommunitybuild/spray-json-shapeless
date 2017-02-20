package fommil.sjs

import RecordFormats._
import spray.json._
import org.scalatest.{FlatSpec, Matchers}
import spray.json.DefaultJsonProtocol
import shapeless.record._
import MixedAst._

class MixedFormatsSpec extends FlatSpec with Matchers with DefaultJsonProtocol {
  implicit val personFormat = jsonFormat2(Person)

  "RecordFormat" should "serialize mixed object" in {
    val address: Address = Record(city = "New Orleans", street = "Bourbon Street", house = "2b")
    val johnDoe = Person("John Doe", address)
    val dept: Department = Record(name = "Nothing", employees = Vector(johnDoe))

    dept.toJson shouldBe JsObject(
      "name" -> JsString("Nothing"),
      "employees" -> JsArray(
        JsObject(
          "name" -> JsString("John Doe"),
          "address" -> JsObject(
            "city" -> JsString("New Orleans"),
            "street" -> JsString("Bourbon Street"),
            "house" -> JsString("2b")
          )
        )
      )
    )
  }

  it should "deserialize mixed object" in {
    val address: Address = Record(city = "New Orleans", street = "Bourbon Street", house = "2b")
    val johnDoe = Person("John Doe", address)
    val dept: Department = Record(name = "Nothing", employees = Vector(johnDoe))

    val jso = JsObject(
      "name" -> JsString("Nothing"),
      "employees" -> JsArray(
        JsObject(
          "name" -> JsString("John Doe"),
          "address" -> JsObject(
            "city" -> JsString("New Orleans"),
            "street" -> JsString("Bourbon Street"),
            "house" -> JsString("2b")
          )
        )
      )
    )

    val resDept = jso.convertTo[Department]

    resDept shouldBe dept
  }
}
