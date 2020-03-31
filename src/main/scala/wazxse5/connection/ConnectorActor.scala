package wazxse5.connection

import java.net.InetSocketAddress

import akka.actor.{ActorRef, Props}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import wazxse5.connection.ConnectorActor.{NewResponse, Send}
import wazxse5.message.{CommandMessage, ResponseMessage}

class ConnectorActor(supervisor: ActorRef, location: NetworkLocation) extends YeelightActor {

  IO(Tcp) ! Connect(new InetSocketAddress(location.address, location.port))

  def ready(connection: ActorRef): Receive = {
    case Send(message) =>
      logger.info(s"Sending to $location message=$message")
      connection ! Write(ByteString(message.text))
    case Received(data) =>
      logger.info(s"${this.getClass.getSimpleName} received from $location message: $data")
    case CommandFailed(_: Write) =>
      supervisor ! "Write failed"
    case _: ConnectionClosed =>
      supervisor ! "Connection closed"
      context.stop(self)
  }

  //

  override def receive: Receive = {
    case CommandFailed => supervisor ! ConnectorActor.ConnectionFailed
    case Connected(_, _) =>
      sender ! Register(self)
      supervisor ! ConnectorActor.Ready
      context.become(ready(sender))
  }
}

object ConnectorActor {
  final object Ready
  final object ConnectionFailed

  case class Send(message: CommandMessage)
  case class NewResponse(responseMessage: ResponseMessage)

  def props(supervisor: ActorRef, location: NetworkLocation): Props = Props(new ConnectorActor(supervisor, location))
}