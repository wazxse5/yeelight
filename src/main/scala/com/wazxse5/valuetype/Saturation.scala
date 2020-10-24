package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class Saturation(value: Int, isBackground: Boolean = false) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Saturation

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0 && value <= 100
}

object Saturation extends PropAndParamCompanion {
  val snapshotName: String = "saturation"
  val paramName: String = "sat"
  val propFgName: String = "sat"
  override val propBgName: String = "bg_sat"
}
