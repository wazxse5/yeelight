package com.wazxse5.core.connection

import akka.actor.{ActorRef, Props}
import akka.io.Udp.{Bound, Received}
import com.wazxse5.api.message.AdvertisementMessage
import com.wazxse5.core.ConnectionAdapter
import com.wazxse5.core.connection.Listener.{Start, Stop}

class Listener(adapter  : ConnectionAdapter) extends ConnectionActor {
  val opts = List(Inet6ProtocolFamily, MultiCastGroup("239.255.255.250"))

//  IO(Udp) ! Bind(self, new InetSocketAddress(1982), opts) // TODO: do zrobienia

  def receiveReady(connection: ActorRef): Receive = {
    case Received(data, sender) =>
      logger.info(s"${this.getClass.getSimpleName} received from $sender")
      val advertisementMessage = AdvertisementMessage(data.utf8String)
      if (advertisementMessage.isValid) adapter.handleMessage(advertisementMessage)
    case Stop =>
      context.become(receiveReadyStop(connection))
  }

  def receiveReadyStop(connection: ActorRef): Receive = {
    case Start => context.become(receiveReady(connection))
  }

  //

  override def receive: Receive = {
    case Bound =>
      context.become(receiveReady(sender))
      logger.info(s"${this.getClass.getSimpleName} is ready")
  }
}

object Listener {
  final case object Start
  final case object Stop

  def props(adapter: ConnectionAdapter): Props = Props(new Listener(adapter))
}
