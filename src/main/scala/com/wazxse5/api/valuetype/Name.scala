package com.wazxse5.api.valuetype

case class Name(value: String) extends Property[String] with Parameter[String] {
  override def propFgName: String = Name.propFgName

  override def propBgName: Option[String] = None

  override def paramName: String = Name.paramName

  override def isBackground: Boolean = false

  override def toJson: JsonValueType[_] = JsonStringValueType(value)

  override def isValid: Boolean = value.nonEmpty
}

object Name {
  val propFgName: String = "name"
  val paramName: String = "name"
}
