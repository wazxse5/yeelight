package com.wazxse5.valuetype
import com.wazxse5.snapshot.SnapshotInfo
import play.api.libs.json.JsNumber

sealed trait FlowPower extends Property[Int] {
  override def companion: PropCompanion = FlowPower

  override def strValue: String = value.toString

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, JsNumber(value))
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

}

final case class FlowOn(isBackground: Boolean) extends FlowPower {
  override val value: Int = 1
}

final case class FlowOff(isBackground: Boolean) extends FlowPower {
  override val value: Int = 0
}

