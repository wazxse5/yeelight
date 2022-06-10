package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Hue(value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  
  override def isValid: Boolean = 0 <= value && value <= 359
}

object Hue {
  val paramName = "hue"
  val propFgName = "hue"
  val propBgName = "bg_hue"
  
  def fromString(str: String): Option[Hue] = Try(new Hue(str.toInt)).filter(_.isValid).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Hue] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}




