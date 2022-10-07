package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, Props}
import akka.io.Udp._
import akka.io.{IO, Udp}
import akka.util.ByteString
import com.wazxse5.yeelight.core.YeelightActor
import com.wazxse5.yeelight.core.message.ServiceMessage.Discover
import com.wazxse5.yeelight.core.message.{DiscoveryMessage, DiscoveryResponseMessage}
import com.wazxse5.yeelight.core.util.Logger

import java.net.{InetSocketAddress, NetworkInterface}
import scala.jdk.CollectionConverters._

class Discoverer(adapter: ActorRef) extends YeelightActor {
  val remote: InetSocketAddress = new InetSocketAddress("239.255.255.250", 1982)

  findLocalInetSocketAddress match {
    case Some(local) => IO(Udp) ! Bind(self, local)
    case None => Logger.error(s"Discoverer cannot find local address to bind")
  }

  def receiveReady(connection: ActorRef): Receive = {
    case Discover =>
      connection ! Send(ByteString(DiscoveryMessage.text), remote)
    case Received(data, _) =>
      val messageOpt = DiscoveryResponseMessage.fromString(data.utf8String)
      messageOpt.foreach(adapter ! _)
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

  override def receive: Receive = {
    case Bound(local) =>
      Logger.info(s"Discoverer is ready (bound to $local)")
      context.become(receiveReady(sender()))
  }

}

object Discoverer {
  def props(connectionAdapter: ActorRef): Props = Props(new Discoverer(connectionAdapter))
}


