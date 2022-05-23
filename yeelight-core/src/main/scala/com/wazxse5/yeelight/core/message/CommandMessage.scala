package com.wazxse5.yeelight.core.message

import com.wazxse5.yeelight.api.command.YeelightCommand
import play.api.libs.json.{JsValue, Json}

import scala.util.Random

case class CommandMessage(
  id: Int,
  deviceId: String,
  command: YeelightCommand
) extends YeelightConnectedMessage {
  
  override def json: JsValue = Json.obj(
    "id" -> id,
    "method" -> command.name,
    "params" -> command.args
  )
  
  override def text: String = Json.stringify(json) + "\r\n"
  
}

object CommandMessage {
  def randomId: Int = Random.nextInt(Int.MaxValue)
  
  def apply(deviceId: String, command: YeelightCommand): CommandMessage =
    new CommandMessage(randomId, deviceId, command)
}