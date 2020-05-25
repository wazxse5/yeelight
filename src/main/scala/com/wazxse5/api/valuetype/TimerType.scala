package com.wazxse5.api.valuetype

case object TimerType extends Parameter[Int] {
  override val paramName: String = "type"

  override val value: Int = 0

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value == 0
}

