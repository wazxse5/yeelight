package com.wazxse5.valuetype

import com.wazxse5.valuetype.Effect.{Smooth, Sudden}

sealed trait Effect extends Parameter[String] {
  override val paramName: String = Effect.paramName

  override def toJson: JsonStringValueType = JsonStringValueType(value)

  override def isValid: Boolean = value == Sudden.value || value == Smooth.value
}

object Effect {
  val paramName = "effect"

  final case object Sudden extends Effect {
    override val value: String = "sudden"
  }

  final case object Smooth extends Effect {
    override val value: String = "smooth"
  }

  def apply(value: String): Effect = value match {
    case Sudden.value => Sudden
    case Smooth.value => Smooth
  }

}

