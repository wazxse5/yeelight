package com.wazxse5.api.valuetype

sealed trait Effect extends Parameter[String] {
  override val paramName: String = Effect.paramName

  override def toJson: JsonStringValueType = JsonStringValueType(value)

  override def isValid: Boolean = value == Sudden.value || value == Smooth.value
}

object Effect {
  val paramName = "effect"

  def apply(value: String): Effect = value match {
    case Sudden.value => Sudden
    case Smooth.value => Smooth
  }

  def sudden: Effect = Sudden

  def smooth: Effect = Smooth

}

case object Sudden extends Effect {
  override val value: String = "sudden"
}

case object Smooth extends Effect {
  override val value: String = "smooth"
}

