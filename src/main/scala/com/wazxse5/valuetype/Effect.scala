package com.wazxse5.valuetype

import com.wazxse5.exception.InvalidParamValueException
import play.api.libs.json.{JsString, JsValue}

sealed trait Effect extends Parameter[String] {
  override def companion: ParamCompanion = Effect

  override def strValue: String = value

  override def paramValue: JsValue = JsString(value)

  override def isValid: Boolean = Effect.values.contains(value)
}

object Effect extends ParamCompanion {
  val snapshotName: String = "effect"
  val paramName: String = "effect"

  case object Sudden extends Effect {
    override val value: String = "sudden"
  }

  case object Smooth extends Effect {
    override val value: String = "smooth"
  }

  def apply(value: String): Effect = value match {
    case Sudden.value => Sudden
    case Smooth.value => Smooth
    case _ => throw new InvalidParamValueException(value)
  }

  def sudden: Effect = Sudden
  def smooth: Effect = Smooth

  def values = Set(Sudden.value, Smooth.value)
}
