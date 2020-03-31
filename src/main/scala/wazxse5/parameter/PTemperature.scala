package wazxse5.parameter

import wazxse5.property.Temperature

case class PTemperature(value: Int) extends Parameter[Int] {
  override val name: String = "ct_value"

  override def toJson: JsonValueType = JsonIntValueType(value)

  override def isValid: Boolean = value >= 1700 && value <= 6500
}

object PTemperature {
  def apply(temperature: Temperature): PTemperature = new PTemperature(temperature.value)

}


