package com.wazxse5.yeelight.valuetype

import play.api.libs.json.JsValue

case class TcpPort(value: Option[Int]) extends Parameter[Int] {
  override def companion: ParamCompanion = TcpPort

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => 0 < v && v <= 65535)
}

object TcpPort extends ParamCompanion {
  val snapshotName: String = "port"
  val paramName: String = "port"
}
