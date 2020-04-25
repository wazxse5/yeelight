package wazxse5.message

import wazxse5.UID
import wazxse5.command.YeelightCommand

import scala.util.Random

case class CommandMessage private(
  id: Int,
  deviceInternalId: UID,
  commandName: String,
  arguments: Seq[String]
) extends IdentifiableMessage {
  override def isValid: Boolean = true

  override def text: String = s"""{"id":${id.toString}, "method":"$commandName", "params":[${arguments.mkString(", ")}]}\r\n"""

}

object CommandMessage {
  def randomId: Int = Random.nextInt(Int.MaxValue)

  def apply(command: YeelightCommand, deviceInternalId: UID): CommandMessage =
    new CommandMessage(randomId, deviceInternalId, command.name, command.args.map(_.jsonValue))
}
