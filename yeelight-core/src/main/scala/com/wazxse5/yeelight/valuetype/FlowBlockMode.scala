package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import com.wazxse5.yeelight.valuetype.FlowBlockMode.snapshotName

sealed trait FlowBlockMode extends ValueType[Int] {

  override def companion: ValueTypeCompanion = FlowBlockMode

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName, ValueType.jsValueOrUnknown(value))

  def isValid: Boolean = value.exists(Set(1, 2, 7).contains)
}

object FlowBlockMode extends ValueTypeCompanion {
  val snapshotName: String = "flowBlockMode"

  def rgb: FlowBlockMode = RgbFlowBlockMode
  def temperature: FlowBlockMode = TemperatureFlowBlockMode
  def sleep: FlowBlockMode = SleepFlowBlockMode

  def apply(value: Int): FlowBlockMode = value match {
    case 1 => RgbFlowBlockMode
    case 2 => TemperatureFlowBlockMode
    case 7 => SleepFlowBlockMode
    case _ => UnknownFlowBlockMode
  }
}

case object RgbFlowBlockMode extends FlowBlockMode {
  override val value: Option[Int] = Some(1)
}

case object TemperatureFlowBlockMode extends FlowBlockMode {
  override val value: Option[Int] = Some(2)
}

case object SleepFlowBlockMode extends FlowBlockMode {
  override val value: Option[Int] = Some(7)
}

case object UnknownFlowBlockMode extends FlowBlockMode {
  override val value: Option[Int] = None
}