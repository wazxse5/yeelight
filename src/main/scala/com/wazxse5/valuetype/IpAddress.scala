package com.wazxse5.valuetype

import play.api.libs.json.{JsString, JsValue}

case class IpAddress(value: String) extends Parameter[String] {
  override def companion: ParamCompanion = IpAddress

  override def strValue: String = value

  override def paramValue: JsValue = JsString(value)

  override def isValid: Boolean = true // TODO walidacja adresu ip

}

object IpAddress extends ParamCompanion {
  val snapshotName: String = "ipAddress"
  val paramName: String = "host"
}