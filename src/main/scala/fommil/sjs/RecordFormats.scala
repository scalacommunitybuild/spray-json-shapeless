package fommil.sjs

import spray.json._
import shapeless._
import shapeless.labelled._

trait KeyResolver {
  def resolve(field: Symbol): String
}

/**
 * Automatic key resolver, returs symbol `as-is`
 */
object AutoKeyResolver extends KeyResolver {
  override def resolve(field: Symbol): String = field.name
}

/**
 * Customizable key resolver, substitutes keys from provided map
 */
class MapBasedKeyResolver(keyMap: Map[Symbol, Symbol]) extends KeyResolver {
  override def resolve(field: Symbol): String = keyMap.getOrElse(field, field).name
}

/**
 * Generic marshallers for extensible records
 */
object RecordFormats {
  /**
   * Formats HNil
   */
  implicit val hNilWriter: JsonWriter[HNil] = (_: HNil) => JsObject()

  /**
   * Formats HList representing extensible record
   */
  implicit def recordWriter[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hWriter: Lazy[JsonWriter[H]],
    tWriter: JsonWriter[T],
    resolver: KeyResolver = AutoKeyResolver
  ): JsonWriter[FieldType[K, H] :: T] =
    (hl: FieldType[K, H] :: T) => {
      val valueName = resolver.resolve(witness.value)
      tWriter.write(hl.tail) match {
        case tjso: JsObject =>
          JsObject(tjso.fields + (valueName -> hWriter.value.write(hl.head)))
        case _ =>
          serializationError("tail serializer must return JsObject")
      }
    }

  /**
   * Reads HNil
   */
  implicit val hNilReader: JsonReader[HNil] =
    (json: JsValue) => json match {
      case JsObject(_) => HNil
      case _           => deserializationError("HNil must be represented as empty object")
    }

  /**
   * Extracts extensible record with given type
   */
  implicit def recordReader[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hReader: Lazy[JsonReader[H]],
    tReader: JsonReader[T],
    resolver: KeyResolver = AutoKeyResolver
  ): JsonReader[FieldType[K, H] :: T] =
    (json: JsValue) => {
      val fieldName = resolver.resolve(witness.value)
      json match {
        case jso: JsObject =>
          jso.getFields(fieldName).headOption match {
            case Some(jsValue) =>
              val hv = hReader.value.read(jsValue)
              val tv = tReader.read(jso)
              field[K](hv) :: tv
            case None =>
              deserializationError(s"Field not found", fieldNames = fieldName :: Nil)
          }
        case _ =>
          deserializationError(s"${json.getClass} can't be deserialized into record type")
      }
    }

  implicit val hNilFormat: JsonFormat[HNil] = new JsonFormat[HNil] {
    override def write(obj: HNil): JsValue = hNilWriter.write(obj)

    override def read(json: JsValue): HNil = hNilReader.read(json)
  }

  implicit def recordFormat[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hFormat: Lazy[JsonFormat[H]],
    tFormat: JsonFormat[T],
    resolver: KeyResolver = AutoKeyResolver
  ): JsonFormat[FieldType[K, H] :: T] = new JsonFormat[FieldType[K, H] :: T] {
    private val writer = recordWriter[K, H, T]
    private val reader = recordReader[K, H, T]

    override def write(obj: FieldType[K, H] :: T): JsValue = writer.write(obj)

    override def read(json: JsValue): FieldType[K, H] :: T = reader.read(json)
  }
}
