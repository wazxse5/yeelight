package com.wazxse5.yeelight.valuetype

import play.api.libs.json.JsValue

sealed trait FlowAction extends Parameter[Int] {
  override def companion: ParamCompanion = FlowAction

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(_ >= 0)
}

object FlowAction extends ParamCompanion {
  val snapshotName: String = "action"
  val paramName: String = "action"

  def recover: FlowAction = FlowActionRecover
  def stay: FlowAction = FlowActionStay
  def turnOff: FlowAction = FlowActionTurnOff
}

/**
 * Recover to the state before the color flow started
 */
case object FlowActionRecover extends FlowAction {
  override val value: Option[Int] = Some(0)
}

/**
 * Stay at the state when the flow is stopped
 */
case object FlowActionStay extends FlowAction {
  override val value: Option[Int] = Some(1)
}

/**
 * Turn off the device after the flow is stopped
 */
case object FlowActionTurnOff extends FlowAction {
  override val value: Option[Int] = Some(2)
}
