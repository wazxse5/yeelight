package com.wazxse5.valuetype

import com.wazxse5.valuetype.ValueType.unknown
import play.api.libs.json.{JsString, JsValue}

case class IpAddress(value: Option[String]) extends Parameter[String] {
  override def companion: ParamCompanion = IpAddress

  override def strValue: String = value.getOrElse(unknown)

  override def paramValue: JsValue = JsString(strValue)

  override def isValid: Boolean = true // TODO walidacja adresu ip

}

object IpAddress extends ParamCompanion {
  val snapshotName: String = "ipAddress"
  val paramName: String = "host"
}