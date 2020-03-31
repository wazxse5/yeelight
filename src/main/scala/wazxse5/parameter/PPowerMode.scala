package wazxse5.parameter

case class PPowerMode(value: Int) extends Parameter[Int] {
  override val name: String = "mode"

  override def toJson: JsonValueType = JsonIntValueType(value)

  def isValid: Boolean = value >= 0 && value <= 5
}

object PPowerMode {
  object Normal extends PPowerMode(0)
  object Ct extends PPowerMode(1)
  object Rgb extends PPowerMode(2)
  object Hsv extends PPowerMode(3)
  object Flow extends PPowerMode(4)
  object Night extends PPowerMode(5)
}
