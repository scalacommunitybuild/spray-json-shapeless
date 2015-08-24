package fommil

import spray.json._
import shapeless._

package object sjs {
  @inline
  def deserError[T: Typeable](msg: String, cause: Throwable = null): Nothing =
    throw new DeserializationException(s"deserialising ${Typeable[T].describe}: $msg", cause)

  @inline
  def unexpectedJson[T: Typeable](got: JsValue): Nothing =
    deserializationError(s"unexpected $got")

  @inline
  def serError[T: Typeable](msg: String): Nothing =
    throw new SerializationException(s"serialising ${Typeable[T].describe}: $msg")

  // a convenience for implicitly[Strict[RootJsonFormat[T]]].value
  // but also consider using shapless' cachedImplicit
  object RootJsonFormat {
    def apply[T](implicit f: Strict[RootJsonFormat[T]]): RootJsonFormat[T] = f.value
  }

  // a convenience for implicitly[Strict[JsonFormat[T]]].value
  // but also consider using shapless' cachedImplicit
  object JsonFormat {
    def apply[T](implicit f: Strict[JsonFormat[T]]): JsonFormat[T] = f.value
  }
}
