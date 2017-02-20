package fommil.sjs

import RecordFormats._
import spray.json._
import org.scalatest.{FlatSpec, Matchers}
import shapeless._
import shapeless.record._
import shapeless.syntax.singleton._

class RecordFormatsSpec extends FlatSpec with Matchers with DefaultJsonProtocol {
  "RecordFormats" should "serialize HNil" in {
    (HNil: HNil).toJson shouldBe JsObject.empty
  }

  it should "serialize record instances" in {
    val book =
      ('author ->> "Benjamin Pierce") ::
        ('title ->> "Types and Programming Languages") ::
        ('id ->> 262162091) ::
        ('price ->> 44.11) ::
        HNil

    book.toJson shouldBe JsObject(
      "author" -> JsString("Benjamin Pierce"),
      "title" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )
  }

  it should "deserialize HNil" in {
    JsObject.empty.convertTo[HNil] shouldBe HNil
  }

  type Book = Record.`'author -> String, 'title -> String, 'id -> Long, 'price -> Double`.T

  it should "deserialize record instances" in {
    val jso = JsObject(
      "author" -> JsString("Benjamin Pierce"),
      "title" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )

    val book = jso.convertTo[Book]
    book('author) shouldBe "Benjamin Pierce"
    book('title) shouldBe "Types and Programming Languages"
    book('id) shouldBe 262162091
    book('price) shouldBe 44.11
  }
}

class CustomizableRecordFormatSpec extends FlatSpec with Matchers with DefaultJsonProtocol {
  implicit val resolver = new MapBasedKeyResolver(Map('title -> 'caption))

  it should "serialize record instances" in {
    val book =
      ('author ->> "Benjamin Pierce") ::
        ('title ->> "Types and Programming Languages") ::
        ('id ->> 262162091) ::
        ('price ->> 44.11) ::
        HNil

    book.toJson shouldBe JsObject(
      "author" -> JsString("Benjamin Pierce"),
      "caption" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )
  }

  type Book = Record.`'author -> String, 'title -> String, 'id -> Long, 'price -> Double`.T

  it should "deserialize record instances" in {
    val jso = JsObject(
      "author" -> JsString("Benjamin Pierce"),
      "caption" -> JsString("Types and Programming Languages"),
      "id" -> JsNumber(262162091),
      "price" -> JsNumber(44.11)
    )

    val book = jso.convertTo[Book]
    book('author) shouldBe "Benjamin Pierce"
    book('title) shouldBe "Types and Programming Languages"
    book('id) shouldBe 262162091
    book('price) shouldBe 44.11
  }
}
