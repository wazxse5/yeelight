package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait MusicPower extends PropAndParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = MusicPower
}

object MusicPower extends PropAndParamCompanion {
  override val name = "musicPower"
  override val paramName = "action"
  override val propFgName = "music_on"

  def on: MusicPower = MusicOn
  def off: MusicPower = MusicOff

  val typeByValue: Map[Int, MusicPower] = Seq(on, off).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[MusicPower] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[MusicPower] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object MusicOn extends MusicPower {
  override val value = 1
  override val strValue = "on"
}

case object MusicOff extends MusicPower {
  override val value = 0
  override val strValue = "off"
}
