package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

sealed trait PowerMode extends Parameter[Int] {
  override def companion: ParamCompanion = PowerMode

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0 && value <= 5
}

object PowerMode extends ParamCompanion {
  val snapshotName: String = "powerMode"
  val paramName: String = "mode"

  def normal: PowerMode = NormalPowerMode
  def temperature: PowerMode = TemperaturePowerMode
  def rgb: PowerMode = RgbPowerMode
  def hsv: PowerMode = HsvPowerMode
  def flow: PowerMode = FlowPowerMode
  def night: PowerMode = NightPowerMode
}

case object NormalPowerMode extends PowerMode {
  override val value: Int = 0
}

case object TemperaturePowerMode extends PowerMode {
  override val value: Int = 1
}

case object RgbPowerMode extends PowerMode {
  override val value: Int = 2
}

case object HsvPowerMode extends PowerMode {
  override val value: Int = 3
}

case object FlowPowerMode extends PowerMode {
  override val value: Int = 4
}

case object NightPowerMode extends PowerMode {
  override val value: Int = 5
}
