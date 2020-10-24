package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class Temperature(value: Int, isBackground: Boolean = false) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Temperature

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 1700 && value <= 6500
}

object Temperature extends PropAndParamCompanion {
  val snapshotName: String = "temperature"
  val paramName: String = "ct_value"
  val propFgName: String = "ct"
  override val propBgName: String = "bg_ct"
}