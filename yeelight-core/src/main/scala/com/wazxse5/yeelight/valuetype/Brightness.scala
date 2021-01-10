package com.wazxse5.yeelight.valuetype
import play.api.libs.json.JsValue


case class Brightness(value: Option[Int], isBackground: Boolean) extends PropAndParam[Int] {

  override def companion: PropAndParamCompanion = Brightness

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => v >= 1 && v <= 100)
}

object Brightness extends PropAndParamCompanion {
  val snapshotName: String = "brightness"
  val paramName: String = "rgb"
  val propFgName: String = "bright"
  override val propBgName: String = "bg_bright"

  def apply(value: Int, isBackground: Boolean = false): Brightness = new Brightness(Some(value), isBackground)
  def unknown(isBackground: Boolean): Brightness = new Brightness(None, isBackground)
  def unknown: Brightness = new Brightness(None, isBackground = false)
}
