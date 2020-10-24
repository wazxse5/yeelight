package com.wazxse5.valuetype

import play.api.libs.json.{JsNull, JsValue}

sealed trait MusicPower extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = MusicPower

  override def strValue: String = value.toString

  override def isBackground: Boolean = false

  override def paramValue: JsValue = JsNull(value)

  override def isValid: Boolean = value == 0 || value == 1
}

object MusicPower extends PropAndParamCompanion {
  val snapshotName: String = "musicPowe"
  val paramName: String = "action" // todo: sprawdzić
  val propFgName: String = "music_on"

  def apply(value: Int): MusicPower = value match {
    case 0 => MusicOn
    case 1 => MusicOff
  }

  def on: MusicPower = MusicOn
  def off: MusicPower = MusicOff
}

case object MusicOn extends MusicPower {
  override val value: Int = 1
}

case object MusicOff extends MusicPower {
  override val value: Int = 0
}
