package com.wazxse5.yeelight.core

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.yeelight.command.{GetProps, YeelightCommand}
import com.wazxse5.yeelight.connection.{ConnectionAdapter, Connector, RealConnectionAdapter}
import com.wazxse5.yeelight.exception.NoSuchDeviceException
import com.wazxse5.yeelight.message._
import com.wazxse5.yeelight.valuetype.{IpAddress, Port}

class YeelightServiceImpl extends YeelightService with StrictLogging {
  private implicit val service: YeelightServiceImpl = this

  private val knownDevices: KnownDevices = new KnownDevices
  private val messageRegistry: MessageRegistry = new MessageRegistry
  override val connectionAdapter: ConnectionAdapter = new RealConnectionAdapter(service = this)

  override def devices: Set[YeelightDevice] = knownDevices.all.map(YeelightDeviceImpl(_)).toSet

  override def deviceInfo(deviceId: String): DeviceInfo = {
    knownDevices.findById(deviceId) match {
      case Some(deviceInfo) => deviceInfo
      case None => throw new NoSuchDeviceException(deviceId)
    }
  }

  override def deviceOf(deviceInfo: DeviceInfo): Option[YeelightDevice] = {
    devices.find(_.deviceId == deviceInfo.deviceId) match {
      case Some(knownDevice) => Some(knownDevice)
      case None if deviceInfo.ipAddress.nonEmpty =>
        knownDevices.findByIp(deviceInfo.ipAddress.get) match {
          case Some(byIp) if byIp.isConnected => None
          case _ =>
            val (address, port) = (deviceInfo.ipAddress.get, deviceInfo.port.getOrElse(Port.default))
            knownDevices.add(deviceInfo)
            connectionAdapter.connect(deviceInfo.deviceId, address, port)
            devices.find(_.deviceId == deviceInfo.deviceId)
        }
      case _ => None
    }
  }

  override def search(): Unit = {
    logger.info(s"Searching for new devices")
    connectionAdapter.search()
  }

  override def startListening(): Unit = {
    logger.info("Start listening")
    connectionAdapter.startListening()
  }

  override def stopListening(): Unit = {
    logger.info("Stop listening")
    connectionAdapter.stopListening()
  }

  override def performCommand(deviceId: String, command: YeelightCommand): Unit = {
    if (knownDevices.contains(deviceId)) {
      val message = CommandMessage(deviceId, command)
      messageRegistry.add(message)
      connectionAdapter.send(message)
    }
    else throw new NoSuchDeviceException(deviceId)
  }

  def handleMessage(message: Message): Unit = message match {
    case controlMessage: ControlMessage => handleControlMessage(controlMessage)
    case yeelightMessage: YeelightMessage => handleYeelightMessage(yeelightMessage)
  }

  private def handleControlMessage(message: ControlMessage): Unit = message match {
    case Connector.ConnectionSucceeded(deviceId) =>
      knownDevices.update(deviceId, isConnected = true)
    case Connector.Disconnected(deviceId) =>
      knownDevices.update(deviceId, isConnected = false)
  }

  private def handleYeelightMessage(message: YeelightMessage): Unit = {
    messageRegistry.add(message)
    message match {
      case a: AdvertisementMessage => handleAdvertisementMessage(a)
      case d: DiscoveryResponseMessage => handleDiscoveryResponseMessage(d)
      case r: CommandResultMessage => handleCommandResultMessage(r)
      case n: NotificationMessage => handleNotificationMessage(n)
      case _ => println(s"Unknown command: $message")
    }
  }

  private def handleAdvertisementMessage(message: AdvertisementMessage): Unit = {
    val address = IpAddress.fromString(message.locationAddress)
    val port = Port.fromString(message.locationPort)
    if (address.nonEmpty && port.nonEmpty) handleAD(
      address.get, port.get,
      DeviceInfo.fromAdvertisement(message),
      DeviceInfoChange.fromAdvertisement(message)
    )
  }

  private def handleDiscoveryResponseMessage(message: DiscoveryResponseMessage): Unit = {
    val address = IpAddress.fromString(message.locationAddress)
    val port = Port.fromString(message.locationPort)
    if (address.nonEmpty && port.nonEmpty) handleAD(
      address.get, port.get,
      DeviceInfo.fromDiscoveryResponse(message),
      DeviceInfoChange.fromDiscoveryResponse(message)
    )
  }

  private def handleAD(address: IpAddress, port: Port, di: => DeviceInfo, dic: => DeviceInfoChange): Unit = {
    knownDevices.findByIp(address) match {
      case Some(device) =>
        device.update(dic)
      case None =>
        val deviceInfo = di
        knownDevices.add(deviceInfo)
        connectionAdapter.connect(deviceInfo.deviceId, address, port)
    }
  }

  private def handleCommandResultMessage(message: CommandResultMessage): Unit = {
    val relatedCommand = messageRegistry.getCommand(message)
    if (relatedCommand.exists(_.commandName == GetProps.commandName)) {
      val deviceInfoChange = DeviceInfoChange.fromGetPropsCommandResult(relatedCommand.get, message)
      knownDevices.update(message.deviceId, deviceInfoChange)
    }
  }

  private def handleNotificationMessage(message: NotificationMessage): Unit = {
    val deviceInfoChange = DeviceInfoChange.fromNotification(message)
    knownDevices.update(message.deviceId, deviceInfoChange)
  }
}
