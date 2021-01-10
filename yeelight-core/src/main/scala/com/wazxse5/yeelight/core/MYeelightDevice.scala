package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.command.YeelightCommand
import com.wazxse5.yeelight.connection.NetworkLocation
import com.wazxse5.yeelight.valuetype.DeviceModel

class MYeelightDevice private(
  val internalId: InternalId,
  val state: YeelightState,
  val service: YeelightService,
) extends YeelightDevice {

  override def id: Option[String] = service.deviceInfo(internalId).id

  override def model: Option[DeviceModel] = service.deviceInfo(internalId).model

  override def location: Option[NetworkLocation] = service.deviceInfo(internalId).location

  override def firmwareVersion: Option[String] = service.deviceInfo(internalId).firmwareVersion

  override def supportedCommands: Option[Set[String]] = service.deviceInfo(internalId).supportedCommands

  override def isConnected: Boolean = service.deviceInfo(internalId).isConnected

  override def performCommand(command: YeelightCommand): Unit = service.performCommand(internalId, command)

}

object MYeelightDevice {
  def apply(deviceInfo: DeviceInfo)(implicit service: YeelightService): MYeelightDevice = {
    val state = new MYeelightState(deviceInfo.internalId, service)
    new MYeelightDevice(deviceInfo.internalId, state, service)
  }

}
