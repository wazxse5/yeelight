package com.wazxse5.yeelight.valuetype

import play.api.libs.json.JsValue

case class FlowCount(value: Option[Int]) extends Parameter[Int] {
  override def companion: ParamCompanion = FlowCount

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(_ >= 0)

}

object FlowCount extends ParamCompanion {
  val snapshotName: String = "flowCount"
  val paramName: String = "count"

  def apply(value: Int): FlowCount = new FlowCount(Some(value))

  def infinite: FlowCount = FlowCount(Some(0))
}
