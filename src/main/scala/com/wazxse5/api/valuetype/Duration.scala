package com.wazxse5.api.valuetype

case class Duration(value: Int) extends Parameter[Int] {
  override val paramName: String = Duration.paramName

  override def toJson: JsonIntValueType = JsonIntValueType(value)

  override def isValid: Boolean = value >= 30
}

object Duration {
  val paramName = "duration"
}
