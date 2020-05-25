package com.wazxse5.api.valuetype

sealed trait Power extends Property[String] with Parameter[String] {
  override val propFgName: String = Power.propFgName

  override val propBgName: Option[String] = Some(Power.propBgName)

  override val paramName: String = Power.paramName

  override def toJson: JsonValueType[_] = JsonStringValueType(value)

  override def isValid: Boolean = Power.values.contains(value)
}

object Power {
  val propFgName: String = "power"
  val propBgName: String = "bg_power"
  val paramName: String = "power"

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
