package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait PowerMode extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = PowerMode
}

object PowerMode extends ParamCompanion {
  override val snapshotName = "powerMode"
  override val paramName = "mode"

  def normal: PowerMode = NormalPowerMode
  def temperature: PowerMode = TemperaturePowerMode
  def rgb: PowerMode = RgbPowerMode
  def hsv: PowerMode = HsvPowerMode
  def flow: PowerMode = FlowPowerMode
  def night: PowerMode = NightPowerMode

  val typeByValue: Map[Int, PowerMode] = Seq(normal, temperature, rgb, hsv, flow, night).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[PowerMode] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[PowerMode] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object NormalPowerMode extends PowerMode {
  override val value = 0
  override val strValue = "normal"
}

case object TemperaturePowerMode extends PowerMode {
  override val value = 1
  override val strValue = "temperature"
}

case object RgbPowerMode extends PowerMode {
  override val value = 2
  override val strValue = "rgb"
}

case object HsvPowerMode extends PowerMode {
  override val value = 3
  override val strValue = "hsv"
}

case object FlowPowerMode extends PowerMode {
  override val value = 4
  override val strValue = "flow"
}

case object NightPowerMode extends PowerMode {
  override val value = 5
  override val strValue = "night"
}
