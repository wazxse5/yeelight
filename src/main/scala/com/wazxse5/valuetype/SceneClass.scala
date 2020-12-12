package com.wazxse5.valuetype

import com.wazxse5.valuetype.ValueType.unknown
import play.api.libs.json.{JsString, JsValue}

sealed trait SceneClass extends Parameter[String] {
  override def companion: ParamCompanion = SceneClass

  override def strValue: String = value.getOrElse(unknown)

  override def paramValue: JsValue = JsString(strValue)

  override def isValid: Boolean = value.exists(Seq("color","hsv", "ct", "cf", "auto_delay_off").contains)
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
  override val value: Option[String] = Some("color")
}

case object HsvSceneClass extends SceneClass {
  override val value: Option[String] = Some("hsv")
}

case object TemperatureSceneClass extends SceneClass {
  override val value: Option[String] = Some("ct")
}

case object FlowSceneClass extends SceneClass {
  override val value: Option[String] = Some("cf")
}

case object DelayOffSceneClass extends SceneClass {
  override val value: Option[String] = Some("auto_delay_off")
}

