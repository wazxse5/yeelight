package wazxse5.parameter

import wazxse5.parameter.PPower.{OFF, ON}

case class PPower(value: String) extends Parameter[String] {
  override val name: String = "power"

  override def toJson: JsonStringValueType = JsonStringValueType(value)

  def isValid: Boolean = value == ON.value || value == OFF.value
}

object PPower {
  object ON extends PPower("on")
  object OFF extends PPower("off")
}
