package com.wazxse5.core

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.api.InternalId
import com.wazxse5.api.message.CommandMessage
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

  def sendMessage(message: CommandMessage): Unit = connector match {
    case Some(connector) if deviceInfo.isConnected =>
      connector ! Send(message)
    case _ =>
      if (deviceInfo.location.nonEmpty) reconnectAndSend(message)
      else logger.warn(s"Cannot send message $message") // TODO: Do refaktoryzacji na później
  }

  def connect(): Unit = deviceInfo.location match {
    case Some(location) =>
      val connector = actorSystem.actorOf(Connector.props(location, internalId, service))
      this.connector = Some(connector)
    case None => logger.warn(s"Cannot reconnect - location is empty")
  }

  def reconnectAndSend(message: CommandMessage): Unit = {
    connect()
    connector.foreach(_ ! Send(message))
  }

  def isConnected: Boolean = deviceInfo.isConnected

  def update(
    location: Option[NetworkLocation] = deviceInfo.location,
    isConnected: Boolean = deviceInfo.isConnected,
    id: Option[String] = deviceInfo.id,
    model: Option[DeviceModel] = deviceInfo.model,
    firmwareVersion: Option[String] = deviceInfo.firmwareVersion,
    supportedCommands: Option[Set[String]] = deviceInfo.supportedCommands,
    //
    brightness: Option[Brightness] = deviceInfo.brightness,
    colorMode: Option[ColorMode] = deviceInfo.colorMode,
    flowExpression: Option[FlowExpression] = deviceInfo.flowExpression,
    flowPower: Option[FlowPower] = deviceInfo.flowPower,
    hue: Option[Hue] = deviceInfo.hue,
    musicPower: Option[MusicPower] = deviceInfo.musicPower,
    name: Option[Name] = deviceInfo.name,
    power: Option[Power] = deviceInfo.power,
    rgb: Option[Rgb] = deviceInfo.rgb,
    saturation: Option[Saturation] = deviceInfo.saturation,
    temperature: Option[Temperature] = deviceInfo.temperature,
    timerValue: Option[TimerValue] = deviceInfo.timerValue,
    //
    bgBrightness: Option[Brightness] = deviceInfo.bgBrightness,
    bgColorMode: Option[ColorMode] = deviceInfo.bgColorMode,
    bgFlowExpression: Option[FlowExpression] = deviceInfo.bgFlowExpression,
    bgFlowPower: Option[FlowPower] = deviceInfo.bgFlowPower,
    bgHue: Option[Hue] = deviceInfo.bgHue,
    bgPower: Option[Power] = deviceInfo.bgPower,
    bgRgb: Option[Rgb] = deviceInfo.bgRgb,
    bgSaturation: Option[Saturation] = deviceInfo.bgSaturation,
    bgTemperature: Option[Temperature] = deviceInfo.bgTemperature,
  ): Unit = {
    deviceInfo = deviceInfo.copy(deviceInfo.internalId, location, isConnected, id, model, firmwareVersion, supportedCommands,
      brightness, colorMode, flowExpression, flowPower, hue, musicPower, name, power, rgb, saturation, temperature, timerValue,
      bgBrightness, bgColorMode, bgFlowExpression, bgFlowPower, bgHue, bgPower, bgRgb, bgSaturation, bgTemperature)
  }

  def update(deviceInfo: DeviceInfo): Unit = this.deviceInfo = deviceInfo

  def update(stateUpdate: StateUpdate): Unit = update(deviceInfo.withStateUpdate(stateUpdate))

  def update(stateUpdate: Option[StateUpdate]): Unit = stateUpdate.foreach(update(_))

}

object KnownDevice {

  def apply(deviceInfo: DeviceInfo)(implicit service: YeelightService, actorSystem: ActorSystem): KnownDevice = {
    val connector = deviceInfo.location.map(l => actorSystem.actorOf(Connector.props(l, deviceInfo.internalId, service)))
    new KnownDevice(deviceInfo, connector, service, actorSystem)
  }

}
