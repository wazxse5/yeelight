package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait PowerMode extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = PowerMode
}

object PowerMode extends ParamCompanion {
  override val name = "powerMode"
  override val paramName = "mode"

  def normal: PowerMode = PowerModeNormal
  def temperature: PowerMode = PowerModeTemperature
  def rgb: PowerMode = PowerModeRgb
  def hsv: PowerMode = PowerModeHsv
  def flow: PowerMode = PowerModeFlow
  def night: PowerMode = PowerModeNight

  val typeByValue: Map[Int, PowerMode] = Seq(normal, temperature, rgb, hsv, flow, night).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[PowerMode] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[PowerMode] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object PowerModeNormal extends PowerMode {
  override val value = 0
  override val strValue = "normal"
}

case object PowerModeTemperature extends PowerMode {
  override val value = 1
  override val strValue = "temperature"
}

case object PowerModeRgb extends PowerMode {
  override val value = 2
  override val strValue = "rgb"
}

case object PowerModeHsv extends PowerMode {
  override val value = 3
  override val strValue = "hsv"
}

case object PowerModeFlow extends PowerMode {
  override val value = 4
  override val strValue = "flow"
}

case object PowerModeNight extends PowerMode {
  override val value = 5
  override val strValue = "night"
}
