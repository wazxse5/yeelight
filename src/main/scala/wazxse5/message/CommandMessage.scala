package wazxse5.message

import wazxse5.command.YeelightCommand

import scala.util.Random

case class CommandMessage private(
  id: Int,
  commandName: String,
  arguments: Seq[String]
) extends YeelightMessage {
  override def isValid: Boolean = true

  override def text: String = {
    s"""
      |{
      |  "id": ${id.toString},
      |  "method": "$commandName",
      |  "params": [${arguments.mkString(", ")}]
      |}
      |""".stripMargin
  }

}

object CommandMessage {
  def randomId: Int = Random.nextInt(Int.MaxValue)

  def apply(command: YeelightCommand): CommandMessage =
    new CommandMessage(randomId, command.name, command.args.map(_.value))
}
