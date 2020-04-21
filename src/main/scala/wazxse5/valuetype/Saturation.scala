package wazxse5.valuetype

final case class Saturation(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propName: String = Saturation.propName

  override val propBgName: String = Saturation.propBgName

  override val paramName: String = Saturation.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 100
}

object Saturation {
  val propName: String = "sat"
  val propBgName: String = "bg_sat"
  val paramName: String = "sat"

}