package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

final case class Percent(value: Int) extends Parameter[Int] {
  override val paramName: String = Percent.paramName

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = -100 <= value && value <= 100
}

object Percent {
  val paramName: String = "percentage"
}

