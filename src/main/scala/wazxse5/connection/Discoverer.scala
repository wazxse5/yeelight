package wazxse5.connection

import java.net.{InetSocketAddress, NetworkInterface}

import akka.actor.{ActorRef, Props}
import akka.io.Udp._
import akka.io.{IO, Udp}
import akka.util.ByteString
import wazxse5.connection.Discoverer.Search
import wazxse5.message.{DiscoveryResponseMessage, DiscoveryMessage}
import wazxse5.model.YeelightService

import scala.collection.JavaConverters._

class Discoverer(service: YeelightService) extends YeelightActor {
  val remote: InetSocketAddress = new InetSocketAddress("239.255.255.250", 1982)

  findLocalInetSocketAddress match {
    case Some(local) => IO(Udp) ! Bind(self, local)
    case None => logger.error(s"cannot find local address to bind")
  }

  def receiveReady(local: InetSocketAddress, connection: ActorRef): Receive = {
    case Search =>
      connection ! Send(ByteString(DiscoveryMessage.text), remote)
    case Received(data, sender) =>
      val discoveryMessage = DiscoveryResponseMessage(data.utf8String)
      if (discoveryMessage.isValid) service.handleMessage(discoveryMessage)
    case Unbind =>
      connection ! Unbind
    case Unbound =>
      connection ! Unbound
      context.stop(self)
  }

  private def findLocalInetSocketAddress: Option[InetSocketAddress] = {
    val interfaces = NetworkInterface.getNetworkInterfaces.asScala.toSet
    val filteredInterfaces = interfaces.filter(i => i.isUp && !i.isLoopback)
    val allAddresses = filteredInterfaces.flatMap(_.getInetAddresses.asScala)
    val reachableLocalAddresses = allAddresses.filter(_.isSiteLocalAddress).filter(_.isReachable(5000))
    reachableLocalAddresses.headOption.map(address => new InetSocketAddress(address, 0))
  }

  //

  override def receive: Receive = {
    case Bound(local) =>
      context.become(receiveReady(local, sender))
      logger.info(s"${this.getClass.getSimpleName} is ready (bound to $local)")
  }

}

object Discoverer {
  final case object Search

  def props(service: YeelightService): Props = Props(new Discoverer(service))
}


