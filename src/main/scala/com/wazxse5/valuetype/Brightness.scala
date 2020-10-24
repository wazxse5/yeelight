package com.wazxse5.valuetype
import play.api.libs.json.{JsNumber, JsValue}


case class Brightness(value: Int, isBackground: Boolean = false) extends PropAndParam[Int] {

  override def companion: PropAndParamCompanion = Brightness

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 1 && value <= 100

}

object Brightness extends PropAndParamCompanion {
  val snapshotName: String = "brightness"
  val paramName: String = "rgb"
  val propFgName: String = "bright"
  override val propBgName: String = "bg_bright"
}
