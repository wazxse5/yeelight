package com.wazxse5.valuetype

import com.wazxse5.exception.InvalidParamValueException
import play.api.libs.json.{JsString, JsValue}

sealed trait Effect extends Parameter[String] {
  override val paramName: String = Effect.paramName

  override def rawValue: String = value

  override def toJson: JsValue = JsString(value)

  override def isValid: Boolean = value == Sudden.value || value == Smooth.value
}

object Effect {
  val paramName = "effect"

  def apply(value: String): Effect = value match {
    case Sudden.value => Sudden
    case Smooth.value => Smooth
    case _ => throw new InvalidParamValueException(value)
  }

  def sudden: Effect = Sudden

  def smooth: Effect = Smooth

  def values = Set(Sudden.value, Smooth.value)

}

case object Sudden extends Effect {
  override val value: String = "sudden"
}

case object Smooth extends Effect {
  override val value: String = "smooth"
}

