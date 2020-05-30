package com.wazxse5.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

sealed trait FlowAction extends Parameter[Int] {
  override val paramName: String = FlowAction.paramName

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0
}

object FlowAction {
  val paramName: String = "action"

  def recover: FlowAction = FlowActionRecover

  def stay: FlowAction = FlowActionStay

  def turnOff: FlowAction = FlowActionTurnOff
}

/**
 * Recover to the state before the color flow started
 */
case object FlowActionRecover extends FlowAction {
  override val value: Int = 0
}

/**
 * Stay at the state when the flow is stopped
 */
case object FlowActionStay extends FlowAction {
  override val value: Int = 1
}

/**
 * Turn off the device after the flow is stopped
 */
case object FlowActionTurnOff extends FlowAction {
  override val value: Int = 2
}


