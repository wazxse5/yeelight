package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.valuetype.ValueType.unknown
import play.api.libs.json.{JsString, JsValue}

sealed trait Power extends PropAndParam[String] {
  override def companion: PropAndParamCompanion = Power

  override def strValue: String = value.getOrElse(unknown)

  override def paramValue: JsValue = JsString(strValue)

  override def isValid: Boolean = value.exists(Power.values.contains)
}

object Power extends PropAndParamCompanion {
  val snapshotName: String = "power"
  val paramName: String = "power"
  val propFgName: String = "power"
  override val propBgName: String = "bg_power"

  def apply(value: String, isBackground: Boolean = false): Power = value match {
    case "on" => PowerOn(isBackground)
    case "off" => PowerOff(isBackground)
    case _ => PowerUnknown(isBackground)
  }

  def on: Power = PowerOn(false)
  def on(isBackground: Boolean): Power = PowerOn(isBackground)

  def off: Power = PowerOff(false)
  def off(isBackground: Boolean): Power = PowerOff(isBackground)

  def unknown: Power = PowerUnknown(false)
  def unknown(isBackground: Boolean): Power = PowerUnknown(isBackground)

  val values = Set("on", "off")
}

final case class PowerOn(isBackground: Boolean) extends Power {
  override val value: Option[String] = Some("on")
}

final case class PowerOff(isBackground: Boolean) extends Power {
  override val value: Option[String] = Some("off")
}

final case class PowerUnknown(isBackground: Boolean) extends Power {
  override val value: Option[String] = None
}
