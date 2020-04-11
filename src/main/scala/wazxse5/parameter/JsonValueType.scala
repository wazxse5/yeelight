package wazxse5.parameter

sealed trait JsonValueType[A] {
  val rawValue: A
  def jsonValue: String
}

object JsonValueType {
  def apply(jsonValue: String): JsonValueType[_] = {
    if (jsonValue.startsWith("\"") && jsonValue.endsWith("\""))
      JsonStringValueType(jsonValue.substring(1, jsonValue.length - 1))
    else if (jsonValue == "true" || jsonValue == "false")
      JsonBooleanValueType(jsonValue.toBoolean)
    else if (jsonValue forall Character.isDigit)
      JsonIntValueType(jsonValue.toInt)
    else
      JsonInvalidValueType(jsonValue)
  }
}

final case class JsonIntValueType(rawValue: Int) extends JsonValueType[Int] {
  override def jsonValue: String = rawValue.toString
}

final case class JsonStringValueType(rawValue: String) extends JsonValueType[String] {
  override def jsonValue: String = "\"" + rawValue + "\""
}

final case class JsonBooleanValueType(rawValue: Boolean) extends JsonValueType[Boolean] {
  override def jsonValue: String = rawValue.toString
}

final case class JsonInvalidValueType(rawValue: String) extends JsonValueType[String] {
  override def jsonValue: String = rawValue
}