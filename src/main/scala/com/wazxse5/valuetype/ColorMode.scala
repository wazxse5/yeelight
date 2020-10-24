package com.wazxse5.valuetype
import com.wazxse5.snapshot.SnapshotInfo
import play.api.libs.json.JsNumber

sealed trait ColorMode extends Property[Int] {

  override def companion: PropCompanion = ColorMode

  override def strValue: String = value.toString

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, JsNumber(value))
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
}

final case class RgbColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Int = 1
}

final case class TemperatureColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Int = 2
}

final case class HsvColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Int = 3
}

