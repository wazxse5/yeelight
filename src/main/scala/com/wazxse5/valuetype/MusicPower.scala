package com.wazxse5.valuetype

import play.api.libs.json.{JsNull, JsValue}

sealed trait MusicPower extends Property[Int] with Parameter[Int] {
  override val propFgName: String = MusicPower.propFgName

  override val propBgName: Option[String] = None

  override val paramName: String = MusicPower.paramName

  override def rawValue: String = value.toString

  override def isBackground: Boolean = false

  override def toJson: JsValue = JsNull(value)

  override def isValid: Boolean = value == 0 || value == 1
}

object MusicPower {
  val propFgName: String = "music_on"
  val paramName: String = "action"

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

