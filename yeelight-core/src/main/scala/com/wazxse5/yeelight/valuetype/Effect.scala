package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsString, JsValue}

import scala.util.Try

sealed trait Effect extends ParamValueType[String] {
  override def strValue: String = value
  override def paramValue: JsValue = JsString(strValue)
  override def companion: ParamCompanion = Effect
}

object Effect extends ParamCompanion {
  override val snapshotName = "effect"
  override val paramName = "effect"

  def sudden: Effect = EffectSudden
  def smooth: Effect = EffectSmooth

  val typeByValue: Map[String, Effect] = Seq(sudden, smooth).map(v => v.value -> v).toMap
  val values: Seq[String] = typeByValue.keys.toSeq

  def fromString(str: String): Option[Effect] = Try(typeByValue(str)).toOption
  def fromJsValue(jsValue: JsValue): Option[Effect] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}

case object EffectSudden extends Effect {
  override val value = "sudden"
}

case object EffectSmooth extends Effect {
  override val value = "smooth"
}