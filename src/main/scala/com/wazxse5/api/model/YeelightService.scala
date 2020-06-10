package com.wazxse5.api.model

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.api.InternalId
import com.wazxse5.api.command.{GetProps, YeelightCommand}
import com.wazxse5.api.message._
import com.wazxse5.core.connection.{Connector, NetworkLocation}
import com.wazxse5.core.{ControlMessage, _}

class YeelightService extends IYeelightService with StrictLogging {
  private implicit val service: YeelightService = this

  private val knownDevices: KnownDevices = new KnownDevices
  private val messageRegistry: MessageRegistry = new MessageRegistry
  override val connectionAdapter: ConnectionAdapter = new RealConnectionAdapter(service = this)

  override def devices: Set[IYeelightDevice] = knownDevices.all.map(YeelightDevice(_)).toSet

  override def deviceInfo(internalId: InternalId): Option[DeviceInfo] = knownDevices.find(internalId)

  override def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice = ??? // TODO:

  override def deviceOf(address: String, port: Int = 55443): IYeelightDevice = {
    val location = NetworkLocation(address, port)
    knownDevices.findByLocation(location) match {
      case Some(existingDevice) =>
        YeelightDevice(existingDevice)
      case None =>
        val newDevice = DeviceInfo(location)
        knownDevices.add(newDevice)
        connectionAdapter.connect(newDevice.internalId, location)
        performCommand(newDevice.internalId, GetProps.all)
        YeelightDevice(newDevice)
    }
  }

  override def search(): Unit = connectionAdapter.search()

  override def startListening(): Unit = connectionAdapter.startListening()

  override def stopListening(): Unit = connectionAdapter.stopListening()

  override def performCommand(internalId: InternalId, command: YeelightCommand): Unit = {
    if (knownDevices.contains(internalId)) {
      val message = CommandMessage(command, internalId)
      messageRegistry.put(message)
      connectionAdapter.send(message)
    }
    else logger.warn(s"Cannot perform command $command") // TODO: Do refaktoryzacji na później
  }

  def handleMessage(message: YeelightMessage): Unit = message match {
    case apiMessage: YeelightApiMessage => handleApiMessage(apiMessage)
    case controlMessage: ControlMessage => handleControlMessage(controlMessage)
  }

  private def handleApiMessage(message: YeelightApiMessage): Unit = message match {
    case deviceInfoMessage: DeviceInfoLike =>
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

  private def handleDeviceInfoMessage(message: DeviceInfoLike): Unit = {
    val location = NetworkLocation(message.location, message.locationPort)
    knownDevices.findByLocation(location) match {
      case Some(device) =>
        val newDeviceInfo = DeviceInfo(message, isConnected = device.isConnected)
        device.update(newDeviceInfo)
      case None =>
        val newDeviceInfo = DeviceInfo(message, isConnected = false)
        knownDevices.add(newDeviceInfo)
        connectionAdapter.connect(newDeviceInfo.internalId, location)
    }
  }

  private def handleResultMessage(message: CommandResultMessage): Unit = {
    messageRegistry.put(message)
    val relatedCommand = messageRegistry.getCommand(message)
    val stateUpdate = relatedCommand.map(StateUpdate(_, message))
    stateUpdate.foreach(knownDevices.update(message.deviceId, _))
  }

}
