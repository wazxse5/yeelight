package com.wazxse5.core

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.api.InternalId
import com.wazxse5.api.command.YeelightCommand
import com.wazxse5.api.message.{CommandMessage, NotificationMessage}
import com.wazxse5.api.model.YeelightService
import com.wazxse5.api.valuetype._
import com.wazxse5.core.connection.Connector.Send
import com.wazxse5.core.connection.{Connector, NetworkLocation}

class KnownDevice private(
  _deviceInfo: DeviceInfo,
  _connector: Option[ActorRef],
  service: YeelightService,
  actorSystem: ActorSystem
) extends StrictLogging {
  var deviceInfo: DeviceInfo = _deviceInfo
  var connector: Option[ActorRef] = _connector

  def internalId: InternalId = deviceInfo.internalId

  def performCommand(command: YeelightCommand): Unit = connector match {
    case Some(connector) if deviceInfo.isConnected =>
      connector ! Send(CommandMessage(command, internalId))
    case _ =>
      if (deviceInfo.location.nonEmpty) reconnectAndSend(command)
      else logger.warn(s"Cannot perform command $command") // TODO: Do refaktoryzacji na później
  }

  def connect(): Unit = deviceInfo.location match {
    case Some(location) =>
      val connector = actorSystem.actorOf(Connector.props(location, internalId, service))
      this.connector = Some(connector)
    case None => logger.warn(s"Cannot reconnect - location is empty")
  }

  def reconnectAndSend(command: YeelightCommand): Unit = {
    connect()
    connector.foreach(_ ! Send(CommandMessage(command, internalId)))
  }

  def isConnected: Boolean = deviceInfo.isConnected

  def update(
    id: Option[String] = deviceInfo.id,
    model: Option[DeviceModel] = deviceInfo.model,
    firmwareVersion: Option[String] = deviceInfo.firmwareVersion,
    supportedCommands: Option[Set[String]] = deviceInfo.supportedCommands,
    power: Option[Power] = deviceInfo.power,
    brightness: Option[Brightness] = deviceInfo.brightness,
    temperature: Option[Temperature] = deviceInfo.temperature,
    rgb: Option[Rgb] = deviceInfo.rgb,
    hue: Option[Hue] = deviceInfo.hue,
    saturation: Option[Saturation] = deviceInfo.saturation,
    colorMode: Option[ColorMode] = deviceInfo.colorMode,
    location: Option[NetworkLocation] = deviceInfo.location,
    isConnected: Boolean = deviceInfo.isConnected
  ): Unit = {
    deviceInfo = deviceInfo.copy(deviceInfo.internalId, location, isConnected, id, model, firmwareVersion, supportedCommands, power,
      brightness, temperature, rgb, hue, saturation, colorMode)
  }

  def update(deviceInfo: DeviceInfo): Unit = this.deviceInfo = deviceInfo

  def update(notification: NotificationMessage): Unit = update(deviceInfo.withNotificationMessageChange(notification))

}

object KnownDevice {

  def apply(deviceInfo: DeviceInfo)(implicit service: YeelightService, actorSystem: ActorSystem): KnownDevice = {
    val connector = deviceInfo.location.map(l => actorSystem.actorOf(Connector.props(l, deviceInfo.internalId, service)))
    new KnownDevice(deviceInfo, connector, service, actorSystem)
  }

}
