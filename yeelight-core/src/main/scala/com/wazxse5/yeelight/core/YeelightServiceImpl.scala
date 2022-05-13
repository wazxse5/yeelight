package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.command.YeelightCommand
import com.wazxse5.yeelight.core.connection.{ConnectionAdapter, Connector}
import com.wazxse5.yeelight.core.message._
import com.wazxse5.yeelight.core.util.{Logger, ObservableListMapBinder}
import com.wazxse5.yeelight.core.valuetype.DeviceModel
import javafx.collections.{FXCollections, ObservableList, ObservableMap}

class YeelightServiceImpl extends YeelightService {
  private val connectionAdapter = new ConnectionAdapter(this)
  
  private val devicesMap: ObservableMap[String, YeelightDeviceImpl] = FXCollections.observableHashMap()
  private val devicesMapListener = new ObservableListMapBinder(devicesMap)
  
  override val devices: ObservableList[YeelightDevice] = devicesMapListener.getList.asInstanceOf[ObservableList[YeelightDevice]]
  
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
    if (devicesMap.containsKey(deviceId)) {
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
        Option(devicesMap.get(deviceId)) match {
          case Some(device) =>
            Logger.info(s"Connected device $deviceId")
            device.update(YeelightStateChange.isConnected(true))
          case None =>
            Logger.error(s"Connected unknown device $deviceId")
        }
      case Connector.Disconnected(deviceId) =>
        Option(devicesMap.get(deviceId)) match {
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
      case m: AdvertisementMessage => handleAdvertisementMessage(m)
      case m: DiscoveryResponseMessage => handleDiscoveryResponseMessage(m)
      case m: CommandResultMessage => handleCommandResultMessage(m)
      case m: NotificationMessage => handleNotificationMessage(m)
      case _ => Logger.error(s"Unknown YeelightMessage: $message")
    }
  }
  
  private def handleAdvertisementMessage(m: AdvertisementMessage): Unit = {
    Logger.warn(s"Unimplemented handle message: $m")
  }
  
  private def handleDiscoveryResponseMessage(m: DiscoveryResponseMessage): Unit = {
    val yeelightStateChange = YeelightStateChange.fromDiscoveryResponse(m)
    Option(devicesMap.get(m.deviceId)) match {
      case Some(device) =>
        device.update(yeelightStateChange)
      case None =>
        val device = new YeelightDeviceImpl(m.deviceId, DeviceModel.fromString(m.model).get, Some(m.firmwareVersion), Some(m.supportedCommands), this)
        device.update(yeelightStateChange)
        devicesMap.put(device.deviceId, device)
        connectionAdapter.connect(device.deviceId, m.address, m.port)
    }
  }
  
  private def handleCommandResultMessage(m: CommandResultMessage): Unit = {
    Logger.warn(s"Unimplemented handle message: $m")
  }
  
  private def handleNotificationMessage(m: NotificationMessage): Unit = {
    Option(devicesMap.get(m.deviceId)) match {
      case Some(device) =>
        device.update(YeelightStateChange.fromNotification(m))
      case None =>
        Logger.warn(s"Notification from unknown device ${m.deviceId}")
    }
  }
  
  override def exit(): Unit = {
    val exit = connectionAdapter.exit
    Logger.info(s"Exit with code $exit")
  }
}
