# spray-json-shapeless

Automatically derive [spray-json][spray-json] `JsonFormat`s, powered by [shapeless][shapeless] (no macros were written during the creation of this library).

For documentation, [read the scaladocs](src/main/scala/fommil/sjs/FamilyFormats.scala) and for examples [read the test cases](src/test/scala/fommil/sjs/FamilyFormatsSpec.scala).

**Please read the documentation - especially the caveats - and examples before raising a ticket.**

## TL;DR

```scala
// always check maven central for the lastest release
libraryDependencies += "com.github.fommil" %% "spray-json-shapeless" % "1.3.0"
```

```scala
import spray.json._
import fommil.sjs.FamilyFormats._

object domain {
  sealed trait SimpleTrait
  case class Foo(s: String) extends SimpleTrait
  case class Bar() extends SimpleTrait
  case object Baz extends SimpleTrait
  case class Faz(o: Option[String]) extends SimpleTrait
}
object use {
  import domain._

  Foo("foo").toJson                // """{"s":"foo"}"""
  Faz(Some("meh")).toJson          // """{"o":"meh"}"""
  Faz(None).toJson                 // """{}"""
  (Foo("foo"): SimpleTrait).toJson // """{"type":"Foo","s":"foo"}"""
  (Bar(): SimpleTrait).toJson      // """{"type":"Bar"}"""
  (Baz: SimpleTrait).toJson        // """{"type":"Baz"}"""
  (Faz(None): SimpleTrait).toJson  // """{"type":"Faz"}"""
}
```
