package com.wazxse5.api.valuetype

sealed trait FlowPower extends Property[Int] {
  override val propFgName: String = FlowPower.propFgName

  override val propBgName: Option[String] = None

}

object FlowPower {
  val propFgName: String = "flowing"
  val propBgName: String = "bg_flowing"

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

