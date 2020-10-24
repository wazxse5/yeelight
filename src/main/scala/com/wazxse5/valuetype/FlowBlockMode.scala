package com.wazxse5.valuetype

import com.wazxse5.snapshot.SnapshotInfo
import com.wazxse5.valuetype.FlowBlockMode.snapshotName
import play.api.libs.json.JsNumber

sealed trait FlowBlockMode extends ValueType[Int] {

  override def companion: ValueTypeCompanion = FlowBlockMode

  override def strValue: String = value.toString

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName, JsNumber(value))

  def isValid: Boolean = {
    value == 1 || value == 2 || value == 7
  }
}

object FlowBlockMode extends ValueTypeCompanion {
  val snapshotName: String = "flowBlockMode"

  def rgb: FlowBlockMode = RgbFlowBlockMode

  def temperature: FlowBlockMode = TemperatureFlowBlockMode

  def sleep: FlowBlockMode = SleepFlowBlockMode
}

case object RgbFlowBlockMode extends FlowBlockMode {
  override val value: Int = 1
}

case object TemperatureFlowBlockMode extends FlowBlockMode {
  override val value: Int = 2
}

case object SleepFlowBlockMode extends FlowBlockMode {
  override val value: Int = 7
}
