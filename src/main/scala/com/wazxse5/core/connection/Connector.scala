package com.wazxse5.core.connection

import java.net.InetSocketAddress

import akka.actor.{ActorRef, Props, Stash}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import com.wazxse5.api.InternalId
import com.wazxse5.api.message.{ApiConnectedMessage, CommandMessage}
import com.wazxse5.api.model.YeelightService
import com.wazxse5.core.ControlMessage
import com.wazxse5.core.connection.Connector.{ConnectionFailed, ConnectionSucceeded, Disconnected, Send}
import play.api.libs.json.Json

class Connector(location: NetworkLocation, deviceInternalId: InternalId, service: YeelightService) extends YeelightActor with Stash {

  IO(Tcp) ! Connect(new InetSocketAddress(location.address, location.port))

  def ready(connection: ActorRef): Receive = {
    case Send(message, _) =>
      connection ! Write(ByteString(message.text))
    case Received(data) =>
      val json = Json.parse(data.utf8String.replace("\r\n",""))
      val message = ApiConnectedMessage.fromJson(json, deviceInternalId)
      if (message.isValid) service.handleMessage(message)
    case CommandFailed(write: Write) =>
      val text = write.data.utf8String
      // TODO: wyciągnięcie internalId komendy i zwrócenie do serwisu
    case _: ConnectionClosed =>
      service.handleMessage(Disconnected(deviceInternalId))
      context.stop(self)
  }

  //

  override def receive: Receive = {
    case Send(_, true) => stash
    case CommandFailed => service.handleMessage(ConnectionFailed(deviceInternalId))
    case Connected(_, _) =>
      service.handleMessage(ConnectionSucceeded(deviceInternalId))
      sender ! Register(self)
      unstashAll()
      context.become(ready(sender))
  }
}

object Connector {
  final case class Send(message: CommandMessage, stash: Boolean = true) extends ControlMessage
  final case object Disconnect extends ControlMessage

  final case class ConnectionFailed(deviceInternalId: InternalId) extends ControlMessage
  final case class ConnectionSucceeded(deviceInternalId: InternalId) extends ControlMessage
  final case class SendFailed(deviceInternalId: InternalId, commandId: Int) extends ControlMessage
  final case class Disconnected(deviceInternalId: InternalId) extends ControlMessage

  def props(location: NetworkLocation, deviceInternalId: InternalId, service: YeelightService): Props =
    Props(new Connector(location, deviceInternalId, service))
}