package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.command.YeelightCommand
import com.wazxse5.yeelight.core.InternalId
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsValue, Json}

import scala.util.Random

case class CommandMessage private(
  id: Int,
  deviceId: InternalId,
  commandName: String,
  arguments: Seq[JsValue]
) extends YeelightConnectedMessage with Identifiable {

  override def isValid: Boolean = true

  override def json: JsValue = Json.obj(
    "id" -> id,
    "method" -> commandName,
    "params" -> arguments
  )

  override def text: String = Json.stringify(json) + "\r\n"

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    "commandMessage", Json.obj(
      "id" -> id,
      "deviceId" -> deviceId.snapshotInfo.value,
      "commandName" -> commandName,
      "arguments" -> arguments
    )
  )
}

object CommandMessage {
  def randomId: Int = Random.nextInt(Int.MaxValue)

  def apply(command: YeelightCommand, deviceInternalId: InternalId): CommandMessage =
    new CommandMessage(randomId, deviceInternalId, command.name, command.args)
}
