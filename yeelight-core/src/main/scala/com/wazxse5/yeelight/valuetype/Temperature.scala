package com.wazxse5.yeelight.valuetype

import play.api.libs.json.JsValue

case class Temperature(value: Option[Int], isBackground: Boolean) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Temperature

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => v >= 1700 && v <= 6500)
}

object Temperature extends PropAndParamCompanion {
  val snapshotName: String = "temperature"
  val paramName: String = "ct_value"
  val propFgName: String = "ct"
  override val propBgName: String = "bg_ct"

  def unknown: Temperature = new Temperature(None, isBackground = false)
  def unknown(isBackground: Boolean): Temperature = new Temperature(None, isBackground)
  def apply(value: Int, isBackground: Boolean = false): Temperature = new Temperature(Some(value), isBackground)
}