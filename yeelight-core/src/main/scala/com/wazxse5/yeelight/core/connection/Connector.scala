package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, Props, Stash}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import com.wazxse5.yeelight.core.YeelightActor
import com.wazxse5.yeelight.core.message.ServiceMessage.{ConnectionFailed, ConnectionSucceeded, DisconnectedDevice, SendCommandMessage}
import com.wazxse5.yeelight.core.message.YeelightConnectedMessage
import play.api.libs.json.Json

import java.net.InetSocketAddress

class Connector(
  deviceId: String,
  address: String,
  port: Int,
  connectionAdapter: ActorRef
) extends YeelightActor with Stash {

  IO(Tcp) ! Connect(new InetSocketAddress(address, port))

  def ready(connection: ActorRef): Receive = {
    case SendCommandMessage(message, _) =>
      connection ! Write(ByteString(message.text))
    case Received(data) =>
      val json = Json.parse(data.utf8String.replace("\r\n",""))
      val message = YeelightConnectedMessage.fromJson(json, deviceId)
      message.foreach(connectionAdapter ! _)
    case CommandFailed(write: Write) =>
      val text = write.data.utf8String
    // TODO: wyciągnięcie internalId komendy i zwrócenie do serwisu
    case _: ConnectionClosed =>
      connectionAdapter ! DisconnectedDevice(deviceId)
      context.stop(self)
  }

  override def receive: Receive = {
    case SendCommandMessage(_, true) => stash()
    case CommandFailed => connectionAdapter ! ConnectionFailed(deviceId)
    case Connected(inetAddress, _) =>
      connectionAdapter ! ConnectionSucceeded(deviceId, inetAddress.getHostName, inetAddress.getPort)
      sender() ! Register(self)
      unstashAll()
      context.become(ready(sender()))
  }
}

object Connector {

  def props(deviceId: String, address: String, port: Int, connectionAdapter: ActorRef): Props =
    Props(new Connector(deviceId, address, port, connectionAdapter))
}