package wazxse5.message

import wazxse5.valuetype.Property

case class NotificationMessage private (
  newProps: Seq[Property[_]],
  isValid: Boolean,
  text: String
) extends ApiConnectedMessage {

}

object NotificationMessage {
  private val startText = """{"method":"props","params":{"""
  private val endText = "}}\r\n"

  def apply(messageText: String): NotificationMessage = {
    try { // TODO: Może da się to ładniej a nie try-catchem
      val m1 = messageText.replace(startText, "").replace(endText, "").replace("\"", "")
      val m2 = m1.split(',').toSeq // params
      val m3 = m2.map(_.split(':')).map(p => (p.head, p.tail.head)) //  pairs of params
      val m4 = m3.map(pair => Property.applyByName(pair._1, pair._2))
      new NotificationMessage(m4, isValid = true, messageText)
    } catch {
      case e: RuntimeException => new NotificationMessage(Seq.empty, isValid = false, "")
    }
  }
}
