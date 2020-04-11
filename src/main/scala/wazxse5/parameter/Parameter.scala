package wazxse5.parameter

trait Parameter[A] {

  val name: String

  val value: A

  def toJson: JsonValueType[_]

  def isValid: Boolean
}
