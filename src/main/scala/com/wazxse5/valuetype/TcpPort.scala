package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class TcpPort(value: Int) extends Parameter[Int] {
  override val paramName: String = TcpPort.paramName

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = {
    0 < value && value <= 65535
  }

}

object TcpPort {
  val paramName: String = "port"
}
