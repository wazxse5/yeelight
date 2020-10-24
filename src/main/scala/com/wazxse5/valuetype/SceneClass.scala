package com.wazxse5.valuetype

import play.api.libs.json.{JsString, JsValue}

sealed trait SceneClass extends Parameter[String] {
  override def companion: ParamCompanion = SceneClass

  override def strValue: String = value

  override def paramValue: JsValue = JsString(value)

  override def isValid: Boolean = Seq("color","hsv", "ct", "cf", "auto_delay_off").contains(value)
}

object SceneClass extends ParamCompanion {
  val snapshotName: String = "sceneClass"
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

