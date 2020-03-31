package wazxse5.parameter

case class PDuration(value: Int) extends Parameter[Int] {
  override val name: String = "duration"

  override def toJson: JsonValueType = JsonIntValueType(value)

  override def isValid: Boolean = value >= 30
}

object PDuration {

}
