package com.wazxse5.valuetype
import com.wazxse5.snapshot.SnapshotInfo
import com.wazxse5.valuetype.ValueType.jsValueOrUnknown

sealed trait FlowPower extends Property[Int] {
  override def companion: PropCompanion = FlowPower

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, jsValueOrUnknown(value))
}

object FlowPower extends PropCompanion {
  val snapshotName: String = "flowPower"
  val propFgName: String = "flowing"
  override val propBgName: String = "bg_flowing"

  def apply(value: Int, isBackground: Boolean = false): FlowPower = value match {
    case 0 => FlowOn(isBackground)
    case 1 => FlowOff(isBackground)
  }

  def on: FlowPower = FlowOn(false)
  def on(isBackground: Boolean): FlowPower = FlowOn(isBackground)

  def off: FlowPower = FlowOff(false)
  def off(isBackground: Boolean): FlowPower = FlowOff(isBackground)

  def unknown(isBackground: Boolean): FlowPower = FlowUnknown(isBackground)
  def unknown: FlowPower = FlowUnknown(isBackground = false)

}

final case class FlowOn(isBackground: Boolean) extends FlowPower {
  override val value: Option[Int] = Some(1)
}

final case class FlowOff(isBackground: Boolean) extends FlowPower {
  override val value: Option[Int] = Some(0)
}

final case class FlowUnknown(isBackground: Boolean) extends FlowPower {
  override val value: Option[Int] = None
}
