package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Port(value: Int) extends ParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = Port
  override def isValid: Boolean = 0 <= value && value <= 65535
}

object Port extends ParamCompanion {
  override val name = "port"
  override val paramName = "port"

  def fromString(str: String): Option[Port] = Try(Port(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Port] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }

  def default: Port = Port(55443)
}
