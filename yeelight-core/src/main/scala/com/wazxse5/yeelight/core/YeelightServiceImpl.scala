package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.command.YeelightCommand
import com.wazxse5.yeelight.core.connection.{ConnectionAdapter, Connector}
import com.wazxse5.yeelight.core.message._
import com.wazxse5.yeelight.core.valuetype.DeviceModel

import scala.collection.concurrent.TrieMap

class YeelightServiceImpl extends YeelightService {
  
  private val devicesMap: TrieMap[String, YeelightDeviceImpl] = TrieMap.empty
  private val connectionAdapter = new ConnectionAdapter(this)
  
  def devices: Seq[YeelightDevice] = devicesMap.values.toSeq
  
  def search(): Unit = {
    Logger.info("Searching for new devices")
    connectionAdapter.search()
  }
  
  def startListening(): Unit = {
    Logger.info("Start listening")
    connectionAdapter.startListening()
  }
  
  def stopListening(): Unit = {
    Logger.info("Stop listening")
    connectionAdapter.stopListening()
  }
  
  def performCommand(deviceId: String, command: YeelightCommand): Unit = {
    if (devicesMap.contains(deviceId)) {
      val message = CommandMessage(deviceId, command)
      connectionAdapter.send(message)
    } else {
      Logger.error(s"Cannot perform command on unknown device $deviceId")
    }
  }
  
  def handleMessage(message: Message): Unit = {
    message match {
      case message: ServiceMessage => handleServiceMessage(message)
      case message: YeelightMessage => handleYeelightMessage(message)
    }
  }
  
  private def handleServiceMessage(message: ServiceMessage): Unit = {
    message match {
      case Connector.ConnectionSucceeded(deviceId) =>
        devicesMap.get(deviceId) match {
          case Some(device) =>
            Logger.info(s"Connected device $deviceId")
            device.update(YeelightStateChange.isConnected(true))
          case None =>
            Logger.error(s"Connected unknown device $deviceId")
        }
      case Connector.Disconnected(deviceId) =>
        devicesMap.get(deviceId) match {
          case Some(device) =>
            Logger.info(s"Disconnected device $deviceId")
            device.update(YeelightStateChange.isConnected(false))
          case None =>
            Logger.error(s"Disconnected unknown device $deviceId")
        }
    }
  }
  
  private def handleYeelightMessage(message: YeelightMessage): Unit = {
    message match {
      case a: AdvertisementMessage => handleAdvertisementMessage(a)
      case d: DiscoveryResponseMessage => handleDiscoveryResponseMessage(d)
      case r: CommandResultMessage => handleCommandResultMessage(r)
      case n: NotificationMessage => handleNotificationMessage(n)
      case _ => Logger.error(s"Unknown YeelightMessage: $message")
    }
  }
  
  private def handleAdvertisementMessage(message: AdvertisementMessage): Unit = {
    Logger.warn(s"Unimplemented handle message: $message")
  }
  
  private def handleDiscoveryResponseMessage(m: DiscoveryResponseMessage): Unit = {
    val yeelightStateChange = YeelightStateChange.fromDiscoveryResponse(m)
    devicesMap.get(m.deviceId) match {
      case Some(device) =>
        device.update(yeelightStateChange)
      case None =>
        val device = new YeelightDeviceImpl(m.deviceId, DeviceModel.fromString(m.model), Some(m.firmwareVersion), Some(m.supportedCommands), this)
        device.update(yeelightStateChange)
        devicesMap.addOne((device.deviceId, device))
        connectionAdapter.connect(device.deviceId, m.address, m.port)
    }
  }
  
  private def handleCommandResultMessage(message: CommandResultMessage): Unit = {
    Logger.warn(s"Unimplemented handle message: $message")
  }
  
  private def handleNotificationMessage(message: NotificationMessage): Unit = {
    Logger.warn(s"Unimplemented handle message: $message")
  }
  
  override def exit(): Unit = {
    val exit = connectionAdapter.exit
    Logger.info(s"Exit with code $exit")
  }
}
