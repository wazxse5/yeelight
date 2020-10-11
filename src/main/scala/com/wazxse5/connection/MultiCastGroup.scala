package com.wazxse5.connection

import java.net.{DatagramSocket, InetAddress, NetworkInterface, StandardProtocolFamily}
import java.nio.channels.DatagramChannel

import akka.io.Inet.{DatagramChannelCreator, SocketOptionV2}

import scala.jdk.CollectionConverters._

final case class MultiCastGroup(address: String) extends SocketOptionV2 {
  override def afterBind(s: DatagramSocket): Unit = {
    val group = InetAddress.getByName(address)
    val interfaces = NetworkInterface.getNetworkInterfaces.asScala.toSet
    val filteredInterfaces = interfaces.filter(i => i.isUp && !i.isLoopback)
    filteredInterfaces.headOption.foreach(i => s.getChannel.join(group, i))
  }
}

object Inet6ProtocolFamily extends DatagramChannelCreator {
  override def create(): DatagramChannel = DatagramChannel.open(StandardProtocolFamily.INET6)
}