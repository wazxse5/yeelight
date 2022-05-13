package com.wazxse5.yeelight.core.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Percent(value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  
  override def isValid: Boolean = -100 <= value && value <= 100
}

object Percent {
  val paramName = "percentage"
  
  def fromString(str: String): Option[Percent] = Try(Percent(str.toInt)).filter(_.isValid).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Percent] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}