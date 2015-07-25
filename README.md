# spray-json-shapeless

Automatically derive [spray-json][spray-json] `JsonFormat`s, powered by [shapeless][shapeless] (no macros were written during the creation of this library).

For documentation, [read the scaladocs](src/main/scala/fommil/sjs/FamilyFormats.scala) and for examples [read the test cases](src/test/scala/fommil/sjs/FamilyFormatsSpec.scala).

**Please read the documentation - especially the caveats - and examples before raising a ticket.**

## TL;DR

```scala
// always check maven central for the lastest release
libraryDependencies += "com.github.fommil" %% "spray-json-shapeless" % "1.1.0"
```

```scala
import spray.json._
import fommil.sjs.FamilyFormats._

package domain {
  sealed trait SimpleTrait
  case class Foo(s: String) extends SimpleTrait
  case class Bar() extends SimpleTrait
  case object Baz extends SimpleTrait
  case class Faz(o: Option[String]) extends SimpleTrait
}
package use {
  import domain._

  Foo("foo").toJson              // """{"s":"foo"}"""
  Faz(Some("meh")).toJson        // """{"o":"meh"}"""
  Faz(None).toJson               // """{}"""
  Foo("foo"): SimpleTrait.toJson // """{"type":"Foo","s":"foo"}"""
  Bar(): SimpleTrait.toJson      // """{"type":"Bar"}"""
  Baz: SimpleTrait.toJson        // """{"type":"Baz"}"""
  Fuzz: SimpleTrait.toJson       // """{"type":"Fuzz"}"""
}
```

## License

`spray-json-shapeless` is [Free Software][free] under the Apache License v2.

I would prefer a [copyleft][copyleft] license because [TypeSafe have set the precedent of closing sources][precedent] and it is extremely concerning that this is happening within our community.

However, both of the primary upstream projects, [spray-json][spray-json] and [shapeless][shapeless], are published under Apache v2, so it is more appropriate to publish this project on the same terms.

[free]: http://www.gnu.org/philosophy/free-sw.en.html
[copyleft]: http://www.gnu.org/copyleft/copyleft.en.html
[precedent]: https://github.com/smootoo/freeslick#history
[spray-json]: https://github.com/spray/spray-json
[shapeless]: https://github.com/milessabin/shapeless
