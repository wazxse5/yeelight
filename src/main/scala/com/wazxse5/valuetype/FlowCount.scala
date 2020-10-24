package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class FlowCount(value: Int) extends Parameter[Int] {
  override def companion: ParamCompanion = FlowCount

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0

}

object FlowCount extends ParamCompanion {
  val snapshotName: String = "flowCount"
  val paramName: String = "count"

  def infinite: FlowCount = FlowCount(0)
}
