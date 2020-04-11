package wazxse5.parameter

import wazxse5.property.Brightness

case class PBrightness(value: Int) extends Parameter[Int] {
  override val name = "brightness"

  override def toJson: JsonIntValueType = JsonIntValueType(value)

  override def isValid: Boolean = value >= 1 && value <= 100
}

object PBrightness {
  def apply(brightness: Brightness): PBrightness = new PBrightness(brightness.value)
}
