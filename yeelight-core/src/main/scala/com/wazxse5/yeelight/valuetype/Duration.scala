package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Duration(value: Int) extends ParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = Duration
  override def isValid: Boolean = value >= 30
}

object Duration extends ParamCompanion {
  override val name = "duration"
  override val paramName = "duration"

  def fromString(str: String): Option[Duration] = Try(Duration(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Duration] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
