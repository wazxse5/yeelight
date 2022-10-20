package com.wazxse5.yeelight.core.message

import akka.actor.ActorRef
import com.wazxse5.yeelight.api.YeelightEventListener
import com.wazxse5.yeelight.core.YeelightDeviceImpl
import com.wazxse5.yeelight.core.util.Logger
import play.api.libs.json.{JsValue, Json}

import scala.util.Try

sealed trait Message

trait ServiceMessage extends Message

trait ServiceConnectionMessage extends ServiceMessage

object ServiceMessage {
  final case class StartYeelightService(connectionAdapter: ActorRef) extends ServiceMessage
  final case class AddEventListener(listener: YeelightEventListener) extends ServiceMessage

  final case object GetDevices extends ServiceMessage
  final case class GetDevicesResponse(devicesMap: Map[String, YeelightDeviceImpl]) extends ServiceMessage

  final case object Discover extends ServiceConnectionMessage
  final case object StartListening extends ServiceConnectionMessage
  final case object StopListening extends ServiceConnectionMessage

  final case class ConnectDevice(deviceId: String, address: String, port: Int) extends ServiceConnectionMessage
  final case class ConnectionSucceeded(deviceId: String, address: String, port: Int) extends ServiceConnectionMessage
  final case class ConnectionFailed(deviceId: String) extends ServiceConnectionMessage
  final case class DisconnectedDevice(deviceId: String) extends ServiceConnectionMessage
  final case class SendCommandMessage(message: CommandMessage, stash: Boolean = true) extends ServiceConnectionMessage
}

trait YeelightMessage extends Message

trait YeelightConnectedMessage extends YeelightMessage {
  val deviceId: String
  def json: JsValue
  def text: String = Json.stringify(json)
}

object YeelightConnectedMessage {

  def fromJson(json: JsValue, deviceId: String): Try[YeelightConnectedMessage] = {
    Try {
      val id = json \ "id"
      val result = json \ "result"
      val error = json \ "error"
      val method = (json \ "method").asOpt[String]

      if (id.isDefined && (result.isDefined || error.isDefined)) {
        CommandResultMessage.fromJson(json, deviceId)
      } else if (method.contains("props")) {
        NotificationMessage.fromJson(json, deviceId)
      } else {
        Logger.errorThrow(s"Unknown YeelightMessage for device=$deviceId (${Json.stringify(json)})")
      }
    }.flatten
  }

}