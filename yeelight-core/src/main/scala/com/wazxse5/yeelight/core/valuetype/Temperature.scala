package com.wazxse5.yeelight.core.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Temperature(value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  
  override def isValid: Boolean = 1700 <= value && value <= 6500
}

object Temperature {
  val paramName = "ct_value"
  val propFgName = "ct"
  val propBgName = "bg_ct"
  
  def fromString(str: String): Option[Temperature] = Try(Temperature(str.toInt)).filter(_.isValid).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Temperature] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
