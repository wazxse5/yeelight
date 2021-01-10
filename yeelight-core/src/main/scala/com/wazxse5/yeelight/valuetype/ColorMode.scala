package com.wazxse5.yeelight.valuetype
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import com.wazxse5.yeelight.valuetype.ValueType.{jsValueOrUnknown, unknown}

sealed trait ColorMode extends Property[Int] {
  override def companion: PropCompanion = ColorMode
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, jsValueOrUnknown(value))
}

object ColorMode extends PropCompanion {
  val snapshotName: String = "colorMode"
  val propFgName: String = "color_mode"
  override val propBgName: String = "bg_lmode"

  def apply(value: Int, isBackground: Boolean = false): ColorMode = value match {
    case 1 => RgbColorMode(isBackground)
    case 2 => TemperatureColorMode(isBackground)
    case 3 => HsvColorMode(isBackground)
  }

  def rgb: ColorMode = RgbColorMode(false)
  def temperature: ColorMode = TemperatureColorMode(false)
  def hsv: ColorMode = HsvColorMode(false)

  def rgb(isBackground: Boolean): ColorMode = RgbColorMode(isBackground)
  def temperature(isBackground: Boolean): ColorMode = TemperatureColorMode(isBackground)
  def hsv(isBackground: Boolean): ColorMode = HsvColorMode(isBackground)

  def unknown(isBackground: Boolean): ColorMode = UnknownColorMode(isBackground)
  def unknown: ColorMode = UnknownColorMode(false)
}

final case class RgbColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Option[Int] = Some(1)
  override val strValue: String = "rgb"
}

final case class TemperatureColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Option[Int] = Some(2)
  override val strValue: String = "temperature"
}

final case class HsvColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Option[Int] = Some(3)
  override val strValue: String = "hsv"
}

final case class UnknownColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Option[Int] = None
  override val strValue: String = unknown
}

