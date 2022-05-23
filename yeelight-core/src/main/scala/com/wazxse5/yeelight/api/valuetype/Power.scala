package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsString, JsValue}

import scala.util.Try

sealed trait Power extends ParamValueType[String] {
  override def paramValue: JsValue = JsString(value)
}

object Power {
  val paramName = "power"
  val propFgName = "power"
  val propBgName = "bg_power"
  
  def on: Power = PowerOn
  
  def off: Power = PowerOff
  
  val typeByValue: Map[String, Power] = Seq(on, off).map(v => v.value -> v).toMap
  
  def fromString(str: String): Option[Power] = Try(typeByValue(str)).toOption
  
  def fromJsValue(jsValue: JsValue): Option[Power] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}

case object PowerOn extends Power {
  override val value = "on"
}

case object PowerOff extends Power {
  override val value = "off"
}