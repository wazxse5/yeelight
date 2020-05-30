package com.wazxse5.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case object TimerType extends Parameter[Int] {
  override val value: Int = 0

  override val paramName: String = "type"

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = value == 0
}

