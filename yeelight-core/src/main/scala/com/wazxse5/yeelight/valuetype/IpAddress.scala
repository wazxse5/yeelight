package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsString, JsValue}

case class IpAddress(value: String) extends ParamValueType[String] {
  override def strValue: String = value
  override def paramValue: JsValue = JsString(value)
  override def companion: ParamCompanion = IpAddress
  override def isValid: Boolean = value.matches(ipRegex)
  private val ipRegex = """^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$"""
}

object IpAddress extends ParamCompanion {
  override val name = "address"
  override val paramName = "host"

  def fromString(str: String): Option[IpAddress] = Some(IpAddress(str)).filter(_.isValid)
  def fromJsValue(jsValue: JsValue): Option[IpAddress] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}