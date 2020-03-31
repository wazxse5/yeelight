package wazxse5.parameter

case class PEffect(value: String) extends Parameter[String] {
  override val name: String = "effect"

  override def toJson: JsonValueType = JsonStringValueType(value)

  override def isValid: Boolean = value == "sudden" || value == "smooth"
}

object PEffect {

  object Sudden extends PEffect("sudden")

  object Smooth extends PEffect("smooth")

}

