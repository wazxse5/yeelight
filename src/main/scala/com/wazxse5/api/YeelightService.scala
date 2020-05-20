package com.wazxse5.api

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.InternalId
import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.{Connector, Discoverer, Listener, NetworkLocation}
import com.wazxse5.message._
import com.wazxse5.model.{DeviceInfo, KnownDevice, KnownDevices}

class YeelightService extends IYeelightService with StrictLogging {
  private implicit val service: YeelightService = this
  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")

  private val discoverer: ActorRef = actorSystem.actorOf(Discoverer.props(service = this))
  private val listener: ActorRef = actorSystem.actorOf(Listener.props(service = this))
  private val knownDevices: KnownDevices = new KnownDevices


  override def devices: Set[IYeelightDevice] = knownDevices.all.map(cdi => YeelightDevice(cdi.deviceInfo)).toSet

  override def deviceInfo(internalId: InternalId): Option[DeviceInfo] = knownDevices.find(internalId).map(_.deviceInfo)

  override def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice = ??? // TODO:

  override def deviceOf(address: String, port: Int = 55443): IYeelightDevice = {
    val location = NetworkLocation(address, port)
    knownDevices.findByLocation(location) match {
      case Some(existingDevice) =>
        YeelightDevice(existingDevice.deviceInfo)
      case None =>
        val newDevice = KnownDevice(DeviceInfo(location))
        knownDevices.add(newDevice)
        YeelightDevice(newDevice.deviceInfo)
    }
  }

  override def search(): Unit = discoverer ! Discoverer.Search

  override def startListening(): Unit = listener ! Listener.Start

  override def stopListening(): Unit = listener ! Listener.Stop

  def performCommand(internalId: InternalId, command: YeelightCommand): Unit = {
    knownDevices.find(internalId) match {
      case Some(device) => device.performCommand(command)
      case None => logger.warn(s"Cannot perform command $command") // TODO: Do refaktoryzacji na później
    }
  }

  def handleMessage(message: Message): Unit = message match {
    case apiMessage: ApiMessage => handleApiMessage(apiMessage)
    case controlMessage: ControlMessage => handleControlMessage(controlMessage)
  }

  private def handleApiMessage(message: ApiMessage): Unit = message match {
    case deviceInfoMessage: DeviceInfoMessage =>
      handleDeviceInfoMessage(deviceInfoMessage)
    case resultMessage: CommandResultMessage =>
      println(s"CommandResultMessage ${resultMessage.text}\tresult=${resultMessage.result}")
    case notification: NotificationMessage =>
      knownDevices.find(notification.deviceInternalId).foreach(_.update(notification))
    case _ =>
      println(s"--Unknown message--  ${message.text}")
  }

  private def handleControlMessage(message: ControlMessage): Unit = message match {
    case Connector.ConnectionSucceeded(deviceInternalId) =>
      knownDevices.find(deviceInternalId).foreach(_.update(isConnected = true))
    case Connector.Disconnected(deviceInternalId) =>
      knownDevices.find(deviceInternalId).foreach(_.update(isConnected = false))
  }

  private def handleDeviceInfoMessage(message: DeviceInfoMessage): Unit = {
    val location = NetworkLocation(message.location, message.locationPort)
    knownDevices.findByLocation(location) match {
      case Some(device) =>
        val newDeviceInfo = DeviceInfo(message, isConnected = device.isConnected)
        device.update(newDeviceInfo)
      case None =>
        knownDevices.add(KnownDevice(DeviceInfo(message, isConnected = false)))
    }
  }
}
