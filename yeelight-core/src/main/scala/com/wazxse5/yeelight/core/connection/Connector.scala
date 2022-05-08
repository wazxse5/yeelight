package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, Props, Stash}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import com.wazxse5.yeelight.core.connection.Connector.{ConnectionFailed, ConnectionSucceeded, Disconnected, Send}
import com.wazxse5.yeelight.core.message.{CommandMessage, ServiceMessage, YeelightConnectedMessage}
import play.api.libs.json.Json

import java.net.InetSocketAddress

class Connector(deviceId: String, address: String, port: Int, adapter: ConnectionAdapter) extends ConnectionActor with Stash {

  IO(Tcp) ! Connect(new InetSocketAddress(address, port))

  def ready(connection: ActorRef): Receive = {
    case Send(message, _) =>
      connection ! Write(ByteString(message.text))
    case Received(data) =>
      val json = Json.parse(data.utf8String.replace("\r\n",""))
      val message = YeelightConnectedMessage.fromJson(json, deviceId)
      message.map(adapter.handleMessage)
    case CommandFailed(write: Write) =>
      val text = write.data.utf8String
    // TODO: wyciągnięcie internalId komendy i zwrócenie do serwisu
    case _: ConnectionClosed =>
      adapter.handleMessage(Disconnected(deviceId))
      context.stop(self)
  }

  //

  override def receive: Receive = {
    case Send(_, true) => stash()
    case CommandFailed => adapter.handleMessage(ConnectionFailed(deviceId))
    case Connected(_, _) =>
      adapter.handleMessage(ConnectionSucceeded(deviceId))
      sender() ! Register(self)
      unstashAll()
      context.become(ready(sender()))
  }
}

object Connector {
  final case class Send(message: CommandMessage, stash: Boolean = true) extends ServiceMessage
  final case object Disconnect extends ServiceMessage

  final case class ConnectionFailed(deviceId: String) extends ServiceMessage
  final case class ConnectionSucceeded(deviceId: String) extends ServiceMessage
  final case class SendFailed(deviceId: String, commandId: Int) extends ServiceMessage
  final case class Disconnected(deviceId: String) extends ServiceMessage

  def props(deviceId: String, address: String, port: Int, adapter: ConnectionAdapter): Props =
    Props(new Connector(deviceId, address, port, adapter))
}