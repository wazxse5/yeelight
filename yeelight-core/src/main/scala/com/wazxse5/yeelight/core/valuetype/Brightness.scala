package com.wazxse5.yeelight.core.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Brightness(value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  
  override def isValid: Boolean = value >= 1 && value <= 100
}

object Brightness {
  val paramName = "brightness"
  val propFgName = "bright"
  val propBgName = "bg_bright"
  val propNlName = "nl_br"
  
  def fromString(str: String): Option[Brightness] = Try(Brightness(str.toInt)).filter(_.isValid).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Brightness] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}