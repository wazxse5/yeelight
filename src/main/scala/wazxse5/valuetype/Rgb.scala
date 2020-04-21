package wazxse5.valuetype


final case class Rgb(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propName: String = Rgb.propName

  override val propBgName: String = Rgb.propBgName

  override val paramName: String = Rgb.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 16777215
}

object Rgb {
  val propName: String = "rgb"
  val propBgName: String = "bg_rgb"
  val paramName: String = "rgb_value"
  // TODO: Dodać metody ułatwiające tworzenie instancji z różnych rzeczy
}
