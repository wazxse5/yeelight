package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import com.wazxse5.yeelight.valuetype.FlowBlockMode.name
import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait FlowBlockMode extends ValueType[Int] {
  override def strValue: String = value.toString
  override def companion: ValueTypeCompanion = FlowBlockMode
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(name, JsNumber(value))
}

object FlowBlockMode extends ValueTypeCompanion {
  override val name = "flowBlockMode"

  def rgb: FlowBlockMode = FlowBlockModeRgb
  def temperature: FlowBlockMode = FlowBlockModeTemperature
  def sleep: FlowBlockMode = FlowBlockModeSleep

  val typeByValue: Map[Int, FlowBlockMode] = Seq(rgb, temperature, sleep).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[FlowBlockMode] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[FlowBlockMode] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object FlowBlockModeRgb extends FlowBlockMode {
  override val value = 1
}

case object FlowBlockModeTemperature extends FlowBlockMode {
  override val value = 2
}

case object FlowBlockModeSleep extends FlowBlockMode {
  override val value = 7
}
