package wazxse5.message

import wazxse5.parameter.JsonValueType

case class CommandResultMessage(
  id: Int,
  result: Seq[JsonValueType[_]],
  text: String
) extends ApiMessage {

  override def isValid: Boolean = true

}

object CommandResultMessage {

  def apply(message: String): CommandResultMessage = {
    // TODO: Napisać to porządnie, z odpornością na błędy
    val m1 = message.substring(1, message.length - 1)
    val m2 = m1.split(',').toSeq.map(_.trim)
    val (m3a, m3b) = (m2.head, m2(1))
    val (m4a, m4b) = (m3a.split(':').toSeq.map(_.trim), m3b.split(':').toSeq.map(_.trim))

    val (idName, idValue) = (m4a.head, m4a(1))
    val (resultName, resultVale) = (m4b.head, m4b(1))
    val resultValues = resultVale.substring(1, resultVale.length - 1).split(',').toSeq.map(_.trim)

    new CommandResultMessage(idValue.toInt,resultValues.map(JsonValueType(_)), message)
  }
}
