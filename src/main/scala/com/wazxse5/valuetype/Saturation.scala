package com.wazxse5.valuetype

import play.api.libs.json.JsValue

case class Saturation(value: Option[Int], isBackground: Boolean) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Saturation

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => v >= 0 && v <= 100)
}

object Saturation extends PropAndParamCompanion {
  val snapshotName: String = "saturation"
  val paramName: String = "sat"
  val propFgName: String = "sat"
  override val propBgName: String = "bg_sat"

  def unknown: Saturation = new Saturation(None, isBackground = false)
  def unknown(isBackground: Boolean): Saturation = new Saturation(None, isBackground)
  def apply(value: Int): Saturation = new Saturation(Some(value), false)
  def apply(value: Int, isBackground: Boolean = false): Saturation = new Saturation(Some(value), isBackground)
}
