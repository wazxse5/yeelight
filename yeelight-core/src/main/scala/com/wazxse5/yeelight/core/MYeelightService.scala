package com.wazxse5.yeelight.core

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.yeelight.command.{GetProps, YeelightCommand}
import com.wazxse5.yeelight.connection.{ConnectionAdapter, Connector, NetworkLocation, RealConnectionAdapter}
import com.wazxse5.yeelight.message._

class MYeelightService extends YeelightService with StrictLogging {
  private implicit val service: MYeelightService = this

  private val knownDevices: KnownDevices = new KnownDevices
  private val messageRegistry: MessageRegistry = new MessageRegistry
  override val connectionAdapter: ConnectionAdapter = new RealConnectionAdapter(service = this)

  override def devices: Set[YeelightDevice] = knownDevices.all.map(MYeelightDevice(_)).toSet

  override def deviceInfo(internalId: InternalId): DeviceInfo = {
    knownDevices.find(internalId) match {
      case Some(deviceInfo) => deviceInfo
      case None => throw new NoSuchElementException() // TODO:
    }
  }

  override def deviceOf(deviceInfo: DeviceInfo): YeelightDevice = ??? // TODO:

  override def deviceOf(address: String, port: Int = 55443): YeelightDevice = {
    logger.info(s"Adding new device (deviceOf) on address=$address port=$port")
    val location = NetworkLocation(address, port)
    knownDevices.findByLocation(location) match {
      case Some(existingDevice) =>
        MYeelightDevice(existingDevice)
      case None =>
        val newDevice = DeviceInfo(location)
        knownDevices.add(newDevice)
        connectionAdapter.connect(newDevice.internalId, location)
        performCommand(newDevice.internalId, GetProps.all)
        MYeelightDevice(newDevice)
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

  override def performCommand(internalId: InternalId, command: YeelightCommand): Unit = {
    logger.info(s"Performing cliCommand on device internalId=$internalId cliCommand=$command")
    if (knownDevices.contains(internalId)) {
      val message = CommandMessage(command, internalId)
      messageRegistry.put(message)
      connectionAdapter.send(message)
    }
    else logger.warn(s"Cannot perform cliCommand $command") // TODO: Do refaktoryzacji na później
  }

  def handleMessage(message: Message): Unit = message match {
    case yeelightMessage: YeelightMessage => handleYeelightMessage(yeelightMessage)
    case controlMessage: ControlMessage => handleControlMessage(controlMessage)
  }

  private def handleYeelightMessage(message: YeelightMessage): Unit = message match {
    case deviceInfoMessage: DeviceInfoMessage =>
      handleDeviceInfoMessage(deviceInfoMessage)
    case resultMessage: CommandResultMessage =>
      handleResultMessage(resultMessage)
    case notification: NotificationMessage =>
      knownDevices.update(notification.deviceId, notification.toStateUpdate)
    case _ =>
      println(s"Unknown message: $message")
  }

  private def handleControlMessage(message: ControlMessage): Unit = message match {
    case Connector.ConnectionSucceeded(deviceInternalId) =>
      knownDevices.update(deviceInternalId, isConnected = true)
    case Connector.Disconnected(deviceInternalId) =>
      knownDevices.update(deviceInternalId, isConnected = false)
  }

  private def handleDeviceInfoMessage(message: DeviceInfoMessage): Unit = {
    val location = NetworkLocation(message.locationAddress, message.locationPort)
    knownDevices.findByLocation(location) match {
      case Some(device) =>
        val newDeviceInfo = DeviceInfo(message, isConnected = device.isConnected)
        logger.info(s"updating device (from deviceInfoMessage) at location=$location")
        device.update(newDeviceInfo)
      case None =>
        val newDeviceInfo = DeviceInfo(message, isConnected = false)
        knownDevices.add(newDeviceInfo)
        logger.info(s"adding device (from deviceInfoMessage) at location=$location")
        connectionAdapter.connect(newDeviceInfo.internalId, location)
    }
  }

  private def handleResultMessage(message: CommandResultMessage): Unit = {
    messageRegistry.put(message)
    val relatedCommand = messageRegistry.getCommand(message)
    val stateUpdate = relatedCommand.map(PropsUpdate(_, message))
    stateUpdate.foreach(knownDevices.update(message.deviceId, _))
  }

}
