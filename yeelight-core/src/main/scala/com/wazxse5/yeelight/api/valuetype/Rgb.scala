package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Rgb(value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  
  override def isValid: Boolean = 1 <= value && value <= 16777215
  
  def red: Int = value >>> 16
  
  def green: Int = value << 16 >>> 24
  
  def blue: Int = value << 24 >>> 24
}

object Rgb {
  val paramName = "rgb_value"
  val propFgName = "rgb"
  val propBgName = "bg_rgb"
  
  def apply(R: Int, G: Int, B: Int): Rgb = new Rgb(R * 65536 + G * 256 + B)
  
  def fromString(str: String): Option[Rgb] = Try(new Rgb(str.toInt)).filter(_.isValid).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Rgb] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
