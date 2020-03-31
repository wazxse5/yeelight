package wazxse5.parameter

import wazxse5.property.Saturation

case class PSaturation(value: Int) extends Parameter[Int] {
  override val name = "sat"

  override def toJson: JsonValueType = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 100
}

object PSaturation {
  def apply(saturation: Saturation): PSaturation = new PSaturation(saturation.value)

}
