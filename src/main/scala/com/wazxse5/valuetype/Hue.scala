package com.wazxse5.valuetype

import play.api.libs.json.JsValue

case class Hue(value: Option[Int], isBackground: Boolean) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Hue

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => v >= 0 && v <= 359)
}

object Hue extends PropAndParamCompanion {
  val snapshotName: String = "hue"
  val paramName: String = "hue"
  val propFgName: String = "hue"
  override val propBgName: String = "bg_hue"

  def unknown: Hue = new Hue(None, isBackground = false)
  def unknown(isBackground: Boolean): Hue = new Hue(None, isBackground)
  def apply(value: Int) = new Hue(Some(value), false)
  def apply(value: Int, isBackground: Boolean) = new Hue(Some(value), isBackground)
}