package com.wazxse5.api.valuetype

import play.api.libs.json.{JsString, JsValue}

case class IpAddress(value: String) extends Parameter[String] {
  override val paramName: String = IpAddress.paramName

  override def rawValue: String = value

  override def toJson: JsValue = JsString(value)

  override def isValid: Boolean = true // TODO walidacja adresu ip

}

object IpAddress {
  val paramName: String = "host"
}