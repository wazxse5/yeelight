package com.wazxse5.api.valuetype

case class IpAddress(value: String) extends Parameter[String] {
  override def paramName: String = IpAddress.paramName

  override def toJson: JsonValueType[_] = JsonStringValueType(value)

  override def isValid: Boolean = true // TODO walidacja adresu ip

}

object IpAddress {
  val paramName: String = "host"
}