package com.wazxse5.valuetype

import com.wazxse5.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.valuetype.FlowBlock.snapshotName
import play.api.libs.json.{JsNumber, JsObject}

case class FlowBlock(
  duration: Duration,
  mode: FlowBlockMode,
  value: Int,
  brightness: Brightness)
  extends Snapshotable {
  // todo: Rozważyć rozszerzenie ValueType

  def toJsonParam: String = s"${duration.value},${mode.value},$value,${brightness.value}"

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, JsObject(Seq(
      duration.snapshotInfo.pair,
      mode.snapshotInfo.pair,
      ("value", JsNumber(value)),
      brightness.snapshotInfo.pair
    ))
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

  def apply(duration: Int, mode: FlowBlockMode, value: Int, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), mode, value, Brightness(brightness))
  }

  def apply(duration: Int, value: Rgb, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), FlowBlockMode.rgb, value.value, Brightness(brightness))
  }

  def apply(duration: Int, value: Temperature, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), FlowBlockMode.temperature, value.value, Brightness(brightness))
  }
}
