package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

class Percent(val value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  
  override def isValid: Boolean = -100 <= value && value <= 100
}

object Percent {
  val paramName = "percentage"
  
  def fromString(str: String): Option[Percent] = Try(new Percent(str.toInt)).filter(_.isValid).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Percent] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}