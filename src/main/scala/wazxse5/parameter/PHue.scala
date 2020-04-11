package wazxse5.parameter

import wazxse5.property.Hue

case class PHue private(value: Int) extends Parameter[Int] {
  override val name: String = "saturation"

  override def toJson: JsonIntValueType = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 359
}

object PHue {
  def apply(hue: Hue): PHue = new PHue(hue.value)

}
