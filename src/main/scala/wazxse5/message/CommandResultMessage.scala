package wazxse5.message

import wazxse5.valuetype.{JsonStringValueType, JsonValueType}

case class CommandResultMessage private (
  id: Int,
  result: Option[Seq[JsonValueType[_]]] = None,
  errorCode: Option[Int] = None,
  errorMessage: Option[String] = None,
  text: String,
  isValid: Boolean = true,
) extends IdentifiableMessage {
  def isOk: Boolean = result.nonEmpty && result.get.length == 1 && result.get.head.asInstanceOf[JsonStringValueType].rawValue == "ok"

  def isError: Boolean = result.isEmpty && errorCode.nonEmpty && errorMessage.nonEmpty
}

object CommandResultMessage {

  def apply(message: String): CommandResultMessage = {
    // TODO: Napisać to porządnie, z odpornością na błędy
    try {
      val m1 = message.substring(1, message.length - 1) // obcięcie zewnętrznych klamer
      val m2 = m1.split(',').toSeq.map(_.trim)
      val m3a = m2.head
      val m4a = m3a.split(':').toSeq.map(_.trim)
      val (idName, idValue) = (m4a.head, m4a(1).toInt)

      val m3b = m2.tail.head.substring(1)
      if (m3b.startsWith("result")) {
        val jvt = readResultMessage(m3b)
        new CommandResultMessage(idValue, result = Some(jvt), text = message)
      }
      else if (m3b.startsWith("error")) {
        val (errorCode, errorMessage) = readErrorMessage(m3b)
        new CommandResultMessage(idValue, errorCode = Some(errorCode), errorMessage = Some(errorMessage), text = message)
      }
      else throw new RuntimeException("invalid message") // TODO: poprawić
    } catch {
      case e: RuntimeException =>
        new CommandResultMessage(-1, text = message, isValid = false)
    }
  }

  private def readErrorMessage(partText: String): (Int, String) = {
    val e1 = partText.replace("\"", "").replace("error:", "")
    val e2 = e1.substring(1, e1.length - 1)
    val e3 = e2.split(',').toSeq.map(_.trim)

    val e3a = e3.head.split(':').toSeq.map(_.trim)
    val (codeName, codeValue) = (e3a.head, e3.head.tail)

    val e3b = e3.tail.head.split(':').toSeq.map(_.trim)
    val (messageName, messageValue) = (e3a.head, e3.head.tail)

    (codeValue.toInt, messageValue)
  }

  private def readResultMessage(partText: String): Seq[JsonValueType[_]] = {
    val r1 = partText.replace("\"","").replace("result:","")
    val r2 = r1.substring(1, r1.length - 1)
    val r3 = r2.split(',').toSeq.map(_.trim)
    r3.map(JsonValueType(_))
  }
}
