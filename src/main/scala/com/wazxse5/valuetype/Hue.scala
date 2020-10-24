package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class Hue(value: Int, isBackground: Boolean = false) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Hue

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0 && value <= 359
}

object Hue extends PropAndParamCompanion {
  val snapshotName: String = "hue"
  val paramName: String = "hue"
  val propFgName: String = "hue"
  override val propBgName: String = "bg_hue"
}