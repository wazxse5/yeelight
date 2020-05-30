package com.wazxse5.api.message

import com.wazxse5.api.InternalId
import com.wazxse5.api.command.YeelightCommand
import play.api.libs.json.{JsValue, Json}

import scala.util.Random

case class CommandMessage private(id: Int, deviceInternalId: InternalId, commandName: String, arguments: Seq[JsValue]) extends IdentifiableMessage {
  override def isValid: Boolean = true

  override def json: JsValue = Json.obj(
    "id" -> id,
    "method" -> commandName,
    "params" -> arguments
  )

  override def text: String = Json.stringify(json) + "\r\n"

}

object CommandMessage {
  def randomId: Int = Random.nextInt(Int.MaxValue)

  def apply(command: YeelightCommand, deviceInternalId: InternalId): CommandMessage =
    new CommandMessage(randomId, deviceInternalId, command.name, command.args)
}
