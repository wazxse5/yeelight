package com.wazxse5.yeelight.valuetype
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait ColorMode extends PropValueType[Int] {
  override def companion: PropCompanion = ColorMode
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.name, JsNumber(value))
}

object ColorMode extends PropCompanion {
  override val name = "colorMode"
  override val propFgName = "color_mode"
  override val propBgName = "bg_lmode"

  def rgb: ColorMode = ColorModeRgb
  def temperature: ColorMode = ColorModeTemperature
  def hsv: ColorMode = ColorModeHsv

  val typeByValue: Map[Int, ColorMode] = Seq(rgb, temperature, hsv).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[ColorMode] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[ColorMode] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object ColorModeRgb extends ColorMode {
  override val value = 1
  override val strValue = "rgb"
}

case object ColorModeTemperature extends ColorMode {
  override val value = 2
  override val strValue = "temperature"
}

case object ColorModeHsv extends ColorMode {
  override val value = 3
  override val strValue = "hsv"
}

