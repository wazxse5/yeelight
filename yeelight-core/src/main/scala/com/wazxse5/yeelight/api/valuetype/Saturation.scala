package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Saturation(value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  
  override def isValid: Boolean = 0 <= value && value <= 100
}

object Saturation {
  val paramName = "sat"
  val propFgName = "sat"
  val propBgName = "bg_sat"
  
  def fromString(str: String): Option[Saturation] = Try(new Saturation(str.toInt)).filter(_.isValid).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Saturation] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}


