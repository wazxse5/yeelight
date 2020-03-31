package wazxse5.parameter

sealed trait JsonValueType {
  def value: String
}

final case class JsonIntValueType(rawValue: Int) extends JsonValueType {
  override def value: String = rawValue.toString
}

final case class JsonStringValueType(rawValue: String) extends JsonValueType {
  override def value: String = "\"" + rawValue + "\""
}

final case class JsonBooleanValueType(rawValue: Boolean) extends JsonValueType {
  override def value: String = rawValue.toString
}