package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsString, JsValue}

import scala.util.Try

sealed trait Power extends PropAndParamValueType[String] {
  override def strValue: String = value
  override def paramValue: JsValue = JsString(value)
  override def companion: PropAndParamCompanion = Power
}

object Power extends PropAndParamCompanion {
  override val snapshotName = "power"
  override val paramName = "power"
  override val propFgName = "power"
  override val propBgName = "bg_power"

  def on: Power = PowerOn
  def off: Power = PowerOff

  val typeByValue: Map[String, Power] = Seq(on, off).map(v => v.value -> v).toMap
  val values: Seq[String] = typeByValue.keys.toSeq

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
