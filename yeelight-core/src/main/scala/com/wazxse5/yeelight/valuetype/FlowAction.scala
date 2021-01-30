package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait FlowAction extends ParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = FlowAction
  override def isValid: Boolean = value >= 0
}

object FlowAction extends ParamCompanion {
  override val snapshotName = "action"
  override val paramName = "action"

  def recover: FlowAction = FlowActionRecover
  def stay: FlowAction = FlowActionStay
  def turnOff: FlowAction = FlowActionTurnOff

  val typeByValue: Map[Int, FlowAction] = Seq(recover, stay, turnOff).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[FlowAction] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[FlowAction] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

/**
 * Recover to the state before the color flow started
 */
case object FlowActionRecover extends FlowAction {
  override val value = 0
}

/**
 * Stay at the state when the flow is stopped
 */
case object FlowActionStay extends FlowAction {
  override val value = 1
}

/**
 * Turn off the device after the flow is stopped
 */
case object FlowActionTurnOff extends FlowAction {
  override val value = 2
}
