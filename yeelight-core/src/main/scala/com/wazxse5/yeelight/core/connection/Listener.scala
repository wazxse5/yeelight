package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, Props}
import akka.io.Udp.{Bound, Received}
import com.wazxse5.yeelight.core.connection.Listener.{Start, Stop}
import com.wazxse5.yeelight.core.message.AdvertisementMessage
import com.wazxse5.yeelight.core.util.Logger

class Listener(adapter: ConnectionAdapter) extends ConnectionActor {

  //  IO(Udp) ! Bind(self, new InetSocketAddress(1982), opts) // TODO: do zrobienia

  def receiveReady(connection: ActorRef): Receive = {
    case Received(data, sender) =>
      val message = AdvertisementMessage.fromString(data.utf8String)
      message.map(adapter.handleMessage)
    case Stop =>
      context.become(receiveReadyStop(connection))
  }

  def receiveReadyStop(connection: ActorRef): Receive = {
    case Start => context.become(receiveReady(connection))
    case Stop => context.become(receive)
  }


  override def receive: Receive = {
    case Bound =>
      context.become(receiveReady(sender()))
      Logger.info("Listener is ready")
  }
}

object Listener {
  final case object Start
  final case object Stop

  def props(adapter: ConnectionAdapter): Props = Props(new Listener(adapter))
}
