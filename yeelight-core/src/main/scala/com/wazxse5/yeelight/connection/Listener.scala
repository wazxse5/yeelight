package com.wazxse5.yeelight.connection

import akka.actor.{ActorRef, Props}
import akka.io.Udp.{Bound, Received}
import com.wazxse5.yeelight.connection.Listener.{Start, Stop}
import com.wazxse5.yeelight.message.AdvertisementMessage

class Listener(adapter: ConnectionAdapter) extends ConnectionActor {
  val opts = List(Inet6ProtocolFamily, MultiCastGroup("239.255.255.250"))

//  IO(Udp) ! Bind(self, new InetSocketAddress(1982), opts) // TODO: do zrobienia

  def receiveReady(connection: ActorRef): Receive = {
    case Received(data, sender) =>
      logger.info(s"${this.getClass.getSimpleName} received from $sender")
      val message = AdvertisementMessage.fromString(data.utf8String)
      message.map(adapter.handleMessage)
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
