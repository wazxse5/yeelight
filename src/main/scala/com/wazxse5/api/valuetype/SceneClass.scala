package com.wazxse5.api.valuetype

import play.api.libs.json.{JsString, JsValue}

sealed trait SceneClass extends Parameter[String] {
  override val paramName: String = SceneClass.paramName

  override def rawValue: String = value

  override def toJson: JsValue = JsString(value)

  override def isValid: Boolean = Seq("color","hsv", "ct", "cf", "auto_delay_off").contains(value)
}

object SceneClass {
  val paramName: String = "class"

  def rgb: SceneClass = RgbSceneClass

  def hsv: SceneClass = HsvSceneClass

  def temperature: SceneClass = TemperatureSceneClass

  def flow: SceneClass = FlowSceneClass

  def delayOff: SceneClass = DelayOffSceneClass

}

case object RgbSceneClass extends SceneClass {
  override val value: String = "color"
}

case object HsvSceneClass extends SceneClass {
  override val value: String = "hsv"
}

case object TemperatureSceneClass extends SceneClass {
  override val value: String = "ct"
}

case object FlowSceneClass extends SceneClass {
  override val value: String = "cf"
}

case object DelayOffSceneClass extends SceneClass {
  override val value: String = "auto_delay_off"
}

