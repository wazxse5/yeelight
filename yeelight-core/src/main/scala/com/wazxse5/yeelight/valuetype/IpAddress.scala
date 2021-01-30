package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsString, JsValue}

case class IpAddress(value: String) extends ParamValueType[String] {
  override def strValue: String = value
  override def paramValue: JsValue = JsString(value)
  override def companion: ParamCompanion = IpAddress
  override def isValid: Boolean = true // TODO walidacja adresu ip
}

object IpAddress extends ParamCompanion {
  override val snapshotName = "address"
  override val paramName = "host"

  def fromString(str: String): Option[IpAddress] = Some(IpAddress(str)).filter(_.isValid)
  def fromJsValue(jsValue: JsValue): Option[IpAddress] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}