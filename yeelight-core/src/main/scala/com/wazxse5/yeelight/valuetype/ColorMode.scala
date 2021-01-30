package com.wazxse5.yeelight.valuetype
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait ColorMode extends PropValueType[Int] {
  override def companion: PropCompanion = ColorMode
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, JsNumber(value))
}

object ColorMode extends PropCompanion {
  override val snapshotName = "colorMode"
  override val propFgName = "color_mode"
  override val propBgName = "bg_lmode"

  def rgb: ColorMode = RgbColorMode
  def temperature: ColorMode = TemperatureColorMode
  def hsv: ColorMode = HsvColorMode

  val typeByValue: Map[Int, ColorMode] = Seq(rgb, temperature, hsv).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[ColorMode] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[ColorMode] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object RgbColorMode extends ColorMode {
  override val value = 1
  override val strValue = "rgb"
}

case object TemperatureColorMode extends ColorMode {
  override val value = 2
  override val strValue = "temperature"
}

case object HsvColorMode extends ColorMode {
  override val value = 3
  override val strValue = "hsv"
}

