package com.wazxse5.yeelight.connection

import java.net.{InetSocketAddress, NetworkInterface}

import akka.actor.{ActorRef, Props}
import akka.io.Udp._
import akka.io.{IO, Udp}
import akka.util.ByteString
import com.wazxse5.yeelight.connection.Discoverer.Search
import com.wazxse5.yeelight.message.{DiscoveryMessage, DiscoveryResponseMessage}

import scala.jdk.CollectionConverters._

class Discoverer(adapter: ConnectionAdapter) extends ConnectionActor {
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
      if (discoveryMessage.isValid) adapter.handleMessage(discoveryMessage)
    case Unbind =>
      connection ! Unbind
    case Unbound =>
      connection ! Unbound
      context.stop(self)
  }

  private def findLocalInetSocketAddress: Option[InetSocketAddress] = {
    val interfaces = NetworkInterface.getNetworkInterfaces.asScala.toSet
    val filteredInterfaces = {
      val filtered = interfaces.filter(i => i.isUp && !i.isLoopback)
      if (filtered.size > 1) filtered.filterNot(_.getDisplayName.toLowerCase.contains("virtual")) else filtered
    }
    val allAddresses = filteredInterfaces.flatMap(_.getInetAddresses.asScala)
    val reachableLocalAddresses = allAddresses.filter(_.isSiteLocalAddress).filter(_.isReachable(5000))
    reachableLocalAddresses.headOption.map(address => new InetSocketAddress(address, 0))
  }

  //

  override def receive: Receive = {
    case Bound(local) =>
      logger.info(s"${this.getClass.getSimpleName} is ready (bound to $local)")
      context.become(receiveReady(local, sender))
  }

}

object Discoverer {
  final case object Search

  def props(adapter: ConnectionAdapter): Props = Props(new Discoverer(adapter))
}


