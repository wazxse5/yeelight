package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, Props}
import com.wazxse5.yeelight.core.YeelightActor
import com.wazxse5.yeelight.core.connection.ConnectionAdapter._
import com.wazxse5.yeelight.core.message.{CommandMessage, Message, ServiceMessage}

class ConnectionAdapter(yeelightServiceImpl: ActorRef) extends YeelightActor {
  
  private val discoverer: ActorRef = system.actorOf(Discoverer.props(self))
  private val listener: ActorRef = system.actorOf(Listener.props(self))
  
  override def receive: Receive = {
    receiveWithConnectors(Map.empty)
  }
  
  def receiveWithConnectors(connectors: Map[String, ActorRef]): Receive = {
    case StartListening =>
      listener ! StartListening
    case StopListening =>
      listener ! StopListening
    case Discover =>
      discoverer ! Discover
    case send: SendMessage =>
      connectors.get(send.message.deviceId) foreach (_ ! send)
    case ConnectDevice(deviceId, address, port) =>
      val connector = system.actorOf(Connector.props(deviceId, address, port, self))
      val appendedConnectors = connectors + ((deviceId, connector))
      context.become(receiveWithConnectors(appendedConnectors))
    case otherMessage: Message =>
      yeelightServiceImpl.forward(otherMessage)
  }
  
}

object ConnectionAdapter {
  final case object Discover extends ServiceMessage
  final case object StartListening extends ServiceMessage
  final case object StopListening extends ServiceMessage
  
  final case class ConnectDevice(deviceId: String, address: String, port: Int) extends ServiceMessage
  final case class ConnectionSucceeded(deviceId: String, address: String, port: Int) extends ServiceMessage
  final case class ConnectionFailed(deviceId: String) extends ServiceMessage
  final case object Disconnect extends ServiceMessage
  final case class Disconnected(deviceId: String) extends ServiceMessage
  
  final case class SendMessage(message: CommandMessage, stash: Boolean = true) extends ServiceMessage
  
  def props(yeelightServiceImplActor: ActorRef): Props = Props(new ConnectionAdapter(yeelightServiceImplActor))
}
