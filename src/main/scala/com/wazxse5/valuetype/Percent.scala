package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class Percent(value: Int) extends Parameter[Int] {
  override def companion: ParamCompanion = Percent

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = -100 <= value && value <= 100
}

object Percent extends ParamCompanion {
  val snapshotName: String = "percent"
  val paramName: String = "percentage"
}

