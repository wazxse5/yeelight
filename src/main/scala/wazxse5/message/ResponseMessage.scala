package wazxse5.message

case class ResponseMessage(
  id: Int,
  result: Seq[String]
) extends YeelightMessage {
  override def isValid: Boolean = true

  override def text: String = ???
}
