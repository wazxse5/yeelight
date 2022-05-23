package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, Props}
import akka.io.Udp.{Bound, Received}
import com.wazxse5.yeelight.core.YeelightActor
import com.wazxse5.yeelight.core.connection.ConnectionAdapter.{StartListening, StopListening}
import com.wazxse5.yeelight.core.message.AdvertisementMessage
import com.wazxse5.yeelight.core.util.Logger

class Listener(connectionAdapter: ActorRef) extends YeelightActor {

  //  IO(Udp) ! Bind(self, new InetSocketAddress(1982), opts) // TODO: do zrobienia

  def receiveReady(connection: ActorRef): Receive = {
    case Received(data, sender) =>
      val message = AdvertisementMessage.fromString(data.utf8String)
      message.map(connectionAdapter ! _)
    case StopListening =>
      context.become(receiveReadyStop(connection))
  }

  def receiveReadyStop(connection: ActorRef): Receive = {
    case StartListening => context.become(receiveReady(connection))
    case StopListening => context.become(receive)
  }


  override def receive: Receive = {
    case Bound =>
      context.become(receiveReady(sender()))
      Logger.info("Listener is ready")
  }
}

object Listener {
  def props(connectionAdapter: ActorRef): Props = Props(new Listener(connectionAdapter))
}
