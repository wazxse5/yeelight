package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsString, JsValue}

sealed trait Effect extends ParamValueType[String] {
  override def paramValue: JsValue = JsString(value)
}


object Effect {
  val paramName = "effect"
  
  def sudden: Effect = EffectSudden
  def smooth: Effect = EffectSmooth
}

case object EffectSudden extends Effect {
  override val value = "sudden"
}

case object EffectSmooth extends Effect {
  override val value = "smooth"
}