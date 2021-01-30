package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class TcpPort(value: Int) extends ParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = TcpPort
  override def isValid: Boolean = 0 < value && value <= 65535
}

object TcpPort extends ParamCompanion {
  override val snapshotName = "port"
  override val paramName = "port"

  def fromString(str: String): Option[TcpPort] = Try(TcpPort(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[TcpPort] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
