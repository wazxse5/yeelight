package com.wazxse5.yeelight.valuetype

import play.api.libs.json.JsValue

case class Percent(value: Option[Int]) extends Parameter[Int] {
  override def companion: ParamCompanion = Percent

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => -100 <= v && v <= 100)
}

object Percent extends ParamCompanion {
  val snapshotName: String = "percent"
  val paramName: String = "percentage"

  def apply(value: Int): Percent = Percent(Some(value))
}

