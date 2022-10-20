package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, Props}
import com.wazxse5.yeelight.core.YeelightActor
import com.wazxse5.yeelight.core.message.Message
import com.wazxse5.yeelight.core.message.ServiceMessage._

class ConnectionAdapter(yeelightServiceActor: ActorRef) extends YeelightActor {

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
    case send: SendCommandMessage =>
      connectors.get(send.message.deviceId) foreach (_ ! send)
    case ConnectDevice(deviceId, address, port) =>
      val connector = system.actorOf(Connector.props(deviceId, address, port, self))
      val appendedConnectors = connectors + ((deviceId, connector))
      context.become(receiveWithConnectors(appendedConnectors))
    case otherMessage: Message =>
      yeelightServiceActor.forward(otherMessage)
  }

}

object ConnectionAdapter {
  def props(yeelightServiceImplActor: ActorRef): Props = Props(new ConnectionAdapter(yeelightServiceImplActor))
}
