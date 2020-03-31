package wazxse5.connection

import java.net.InetSocketAddress

import akka.actor.{ActorRef, Props}
import akka.io.Udp.{Bind, Bound, Received}
import akka.io.{IO, Udp}
import wazxse5.connection.ListenerActor.Ready
import wazxse5.message.AdvertisementMessage

class ListenerActor(supervisor: ActorRef) extends YeelightActor {
  val opts = List(Inet6ProtocolFamily, MultiCastGroup("239.255.255.250"))

//  IO(Udp) ! Bind(self, new InetSocketAddress(1982), opts) // TODO: do zrobienia
  supervisor ! Ready
  logger.info(s"${this.getClass.getSimpleName} is ready")

  def receiveReady(connection: ActorRef): Receive = {
    case Received(data, sender) =>
      logger.info(s"${this.getClass.getSimpleName} received from $sender")
      println(data.utf8String)
  }

  override def receive: Receive = {
    case Bound =>
      context.become(receiveReady(sender))
      supervisor ! ListenerActor.Ready
      logger.info(s"${this.getClass.getSimpleName} is ready")
  }
}

object ListenerActor {

  final object Ready

  final object Start

  final object Stop

  final case class NewAdvertisement(advertisementMessage: AdvertisementMessage)

  def props(supervisor: ActorRef): Props = Props(new ListenerActor(supervisor))
}
