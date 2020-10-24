package com.wazxse5.valuetype

import play.api.libs.json.{JsString, JsValue}

sealed trait Power extends PropAndParam[String] {
  override def companion: PropAndParamCompanion = Power

  override def strValue: String = value

  override def paramValue: JsValue = JsString(value)

  override def isValid: Boolean = Power.values.contains(value)
}

object Power extends PropAndParamCompanion {
  val snapshotName: String = "power"
  val paramName: String = "power"
  val propFgName: String = "power"
  override val propBgName: String = "bg_power"

  def apply(value: String, isBackground: Boolean = false): Power = value match {
    case "on" => PowerOn(isBackground)
    case "off" => PowerOff(isBackground)
  }

  def on: Power = PowerOn(false)
  def on(isBackground: Boolean): Power = PowerOn(isBackground)

  def off: Power = PowerOff(false)
  def off(isBackground: Boolean): Power = PowerOff(isBackground)

  val values = Set("on", "off")
}

final case class PowerOn(isBackground: Boolean) extends Power {
  override val value: String = "on"
}

final case class PowerOff(isBackground: Boolean) extends Power {
  override val value: String = "off"
}
