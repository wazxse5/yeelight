package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.yeelight.valuetype.FlowBlock.snapshotName
import play.api.libs.json.Json

case class FlowBlock(
  duration: Duration,
  mode: FlowBlockMode,
  value: Int,
  brightness: Brightness)
  extends Snapshotable {
  // todo: Rozważyć rozszerzenie ValueType

  def toJsonParam: String = s"${duration.value},${mode.value},$value,${brightness.value}"

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, Json.obj(
      duration.snapshotInfo.pairw,
      mode.snapshotInfo.pairw,
      "value" -> value,
      brightness.snapshotInfo.pairw
    )
  )

  def isValid: Boolean = {
    duration.isValid && mode.isValid && brightness.isValid && isValueValid
  }

  private def isValueValid: Boolean = mode match {
    case RgbFlowBlockMode => Rgb(value).isValid
    case TemperatureFlowBlockMode => Temperature(value).isValid
    case SleepFlowBlockMode => true
  }
}

object FlowBlock {
  val snapshotName: String = "flowBlock"

  def apply(d: String, m: String, v: String, b: String): FlowBlock = {
    new FlowBlock(Duration(d.toInt), FlowBlockMode(m.toInt), v.toInt, Brightness(b.toInt))
  }

  def apply(duration: Int, mode: FlowBlockMode, value: Int, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), mode, value, Brightness(brightness))
  }

  def apply(duration: Int, value: Rgb, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), FlowBlockMode.rgb, value.value.get, Brightness(brightness))
  }

  def apply(duration: Int, value: Temperature, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), FlowBlockMode.temperature, value.value.get, Brightness(brightness))
  }
}
