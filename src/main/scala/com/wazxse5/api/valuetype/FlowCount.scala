package com.wazxse5.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class FlowCount(value: Int) extends Parameter[Int] {
  override val paramName: String = FlowCount.paramName

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0

}

object FlowCount {
  val paramName = "count"

  def infinite: FlowCount = FlowCount(0)
}
