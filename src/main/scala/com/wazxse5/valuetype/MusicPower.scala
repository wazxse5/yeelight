package com.wazxse5.valuetype

import play.api.libs.json.JsValue

sealed trait MusicPower extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = MusicPower

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def isBackground: Boolean = false

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => v == 0 || v == 1)
}

object MusicPower extends PropAndParamCompanion {
  val snapshotName: String = "musicPower"
  val paramName: String = "action"
  val propFgName: String = "music_on"

  def apply(value: Int): MusicPower = value match {
    case 0 => MusicOn
    case 1 => MusicOff
  }

  def on: MusicPower = MusicOn
  def off: MusicPower = MusicOff
  def unknown: MusicPower = MusicUnknown
}

case object MusicOn extends MusicPower {
  override val value: Option[Int] = Some(1)
}

case object MusicOff extends MusicPower {
  override val value: Option[Int] = Some(0)
}

case object MusicUnknown extends MusicPower {
  override val value: Option[Int] = None
}