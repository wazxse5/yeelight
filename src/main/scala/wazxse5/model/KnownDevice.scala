package wazxse5.model

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.StrictLogging
import wazxse5.UID
import wazxse5.command.YeelightCommand
import wazxse5.connection.Connector.Send
import wazxse5.connection.{Connector, NetworkLocation}
import wazxse5.message.{CommandMessage, NotificationMessage}
import wazxse5.valuetype._

class KnownDevice private( // TODO: Nazwa do przemyślenia
  _deviceInfo: DeviceInfo,
  _connector: Option[ActorRef],
  service: YeelightService,
  actorSystem: ActorSystem
) extends StrictLogging {
  var deviceInfo: DeviceInfo = _deviceInfo
  var connector: Option[ActorRef] = _connector

  def internalId: UID = deviceInfo.internalId

  def performCommand(command: YeelightCommand): Unit = connector match {
    case Some(connector) if deviceInfo.isConnected =>
      connector ! Send(CommandMessage(command, internalId))
    case _ =>
      if (deviceInfo.location.nonEmpty) reconnectAndSend(command)
      else logger.warn(s"Cannot perform command $command") // TODO: Do refaktoryzacji na później
  }

  def reconnectAndSend(command: YeelightCommand): Unit = deviceInfo.location match {
    case Some(location) =>
      val connector = actorSystem.actorOf(Connector.props(location, internalId, service))
      connector ! Send(CommandMessage(command, internalId))
      this.connector = Some(connector)
    case None => logger.warn(s"Cannot reconnect - location is empty")
  }

  def setDeviceInfo(newDeviceInfo: DeviceInfo): Unit = deviceInfo = newDeviceInfo

  def updateDeviceInfo(
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
    deviceInfo = deviceInfo.withValue(id, model, firmwareVersion, supportedCommands, power, brightness,
      temperature, rgb, hue, saturation, colorMode, location, isConnected)
  }

  def updateDeviceInfo(notification: NotificationMessage): Unit = {
    setDeviceInfo(deviceInfo.withNotificationMessageChange(notification))
  }

}

object KnownDevice {

  def apply(deviceInfo: DeviceInfo)(implicit service: YeelightService, actorSystem: ActorSystem): KnownDevice = {
    val connector = deviceInfo.location.map(l => actorSystem.actorOf(Connector.props(l, deviceInfo.internalId, service)))
    new KnownDevice(deviceInfo, connector, service, actorSystem)
  }

}
