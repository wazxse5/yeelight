package wazxse5.valuetype

final case class Hue(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propName: String = Hue.propName

  override val propBgName: String = Hue.propBgName

  override val paramName: String = Hue.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 359
}

object Hue {
  val propName: String = "hue"
  val propBgName: String = "bg_hue"
  val paramName: String = "hue"
}