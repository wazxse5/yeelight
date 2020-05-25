package com.wazxse5.api.valuetype

case class FlowCount(value: Int) extends Parameter[Int] {
  override def paramName: String = FlowCount.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0

}

object FlowCount {
  val paramName = "count"

  def infinite: FlowCount = FlowCount(0)
}
