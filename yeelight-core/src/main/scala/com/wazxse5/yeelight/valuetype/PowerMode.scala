package com.wazxse5.yeelight.valuetype

import play.api.libs.json.JsValue

sealed trait PowerMode extends Parameter[Int] {
  override def companion: ParamCompanion = PowerMode

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => v >= 0 && v <= 5)
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
  override val value: Option[Int] = Some(0)
  override val strValue: String = "normal"
}

case object TemperaturePowerMode extends PowerMode {
  override val value: Option[Int] = Some(1)
  override val strValue: String = "temperature"
}

case object RgbPowerMode extends PowerMode {
  override val value: Option[Int] = Some(2)
  override val strValue: String = "rgb"
}

case object HsvPowerMode extends PowerMode {
  override val value: Option[Int] = Some(3)
  override val strValue: String = "hsv"
}

case object FlowPowerMode extends PowerMode {
  override val value: Option[Int] = Some(4)
  override val strValue: String = "flow"
}

case object NightPowerMode extends PowerMode {
  override val value: Option[Int] = Some(5)
  override val strValue: String = "night"
}
