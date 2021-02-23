package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsString, JsValue}

import scala.util.Try

sealed trait SceneClass extends ParamValueType[String] {
  override def strValue: String = value
  override def paramValue: JsValue = JsString(value)
  override def companion: ParamCompanion = SceneClass
}

object SceneClass extends ParamCompanion {
  override val name = "sceneClass"
  override val paramName = "class"

  def rgb: SceneClass = SceneClassRgb
  def hsv: SceneClass = SceneClassHsv
  def temperature: SceneClass = SceneClassTemperature
  def flow: SceneClass = SceneClassFlow
  def delayOff: SceneClass = SceneClassDelayOff

  val typeByValue: Map[String, SceneClass] = Seq(rgb, hsv, temperature, flow, delayOff).map(v => v.value -> v).toMap
  val values: Seq[String] = typeByValue.keys.toSeq

  def fromString(str: String): Option[SceneClass] = Try(typeByValue(str)).toOption
  def fromJsValue(jsValue: JsValue): Option[SceneClass] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}

case object SceneClassRgb extends SceneClass {
  override val value = "color"
}

case object SceneClassHsv extends SceneClass {
  override val value = "hsv"
}

case object SceneClassTemperature extends SceneClass {
  override val value = "ct"
}

case object SceneClassFlow extends SceneClass {
  override val value = "cf"
}

case object SceneClassDelayOff extends SceneClass {
  override val value = "auto_delay_off"
}

