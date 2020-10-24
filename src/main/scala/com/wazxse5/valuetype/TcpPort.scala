package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class TcpPort(value: Int) extends Parameter[Int] {
  override def companion: ParamCompanion = TcpPort

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = {
    0 < value && value <= 65535
  }

}

object TcpPort extends ParamCompanion {
  val snapshotName: String = "port"
  val paramName: String = "port"
}
