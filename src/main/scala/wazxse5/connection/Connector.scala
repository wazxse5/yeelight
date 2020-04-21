package wazxse5.connection

import java.net.InetSocketAddress

import akka.actor.{ActorRef, Props, Stash}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import wazxse5.connection.Connector.{ConnectionFailed, ConnectionSucceeded, Disconnected, Send}
import wazxse5.message.{ApiConnectedMessage, CommandMessage, ControlMessage}
import wazxse5.model.YeelightService

class Connector(location: NetworkLocation, service: YeelightService) extends YeelightActor with Stash {

  IO(Tcp) ! Connect(new InetSocketAddress(location.address, location.port))

  def ready(connection: ActorRef): Receive = {
    case Send(message, _) =>
      println(s"sending command to $location text=${message.text}")
      connection ! Write(ByteString(message.text))
    case Received(data) =>
      val message = ApiConnectedMessage.fromMessageText(data.utf8String)
      if (message.isValid) service.handleMessage(message)
    case CommandFailed(write: Write) =>
      val text = write.data.utf8String
      // TODO: wyciągnięcie internalId komendy i zwrócenie do serwisu
    case _: ConnectionClosed =>
      service.handleMessage(Disconnected(location))
      context.stop(self)
  }

  //

  override def receive: Receive = {
    case Send(_, true) => stash
    case CommandFailed => service.handleMessage(ConnectionFailed(location))
    case Connected(_, _) =>
      service.handleMessage(ConnectionSucceeded(location))
      sender ! Register(self)
      unstashAll()
      context.become(ready(sender))
  }
}

object Connector {
  final case class Send(message: CommandMessage, stash: Boolean = false) extends ControlMessage
  final case object Disconnect extends ControlMessage

  final case class ConnectionFailed(location: NetworkLocation) extends ControlMessage
  final case class ConnectionSucceeded(location: NetworkLocation) extends ControlMessage
  final case class SendFailed(location: NetworkLocation, commandId: Int) extends ControlMessage
  final case class Disconnected(location: NetworkLocation) extends ControlMessage

  def props(location: NetworkLocation, service: YeelightService): Props = Props(new Connector(location, service))
}