package com.wazxse5.core

import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.NetworkLocation
import com.wazxse5.valuetype.DeviceModel

class MYeelightDevice private(
  val internalId: InternalId,
  val state: YeelightState,
  val service: YeelightService,
) extends YeelightDevice {

  override def id: Option[String] = service.deviceInfo(internalId).flatMap(_.id)

  override def model: Option[DeviceModel] = service.deviceInfo(internalId).flatMap(_.model)

  override def location: Option[NetworkLocation] = service.deviceInfo(internalId).flatMap(_.location)

  override def firmwareVersion: Option[String] = service.deviceInfo(internalId).flatMap(_.firmwareVersion)

  override def supportedCommands: Option[Set[String]] = service.deviceInfo(internalId).flatMap(_.supportedCommands)

  override def isConnected: Boolean = service.deviceInfo(internalId).exists(_.isConnected)

  override def performCommand(command: YeelightCommand): Unit = service.performCommand(internalId, command)

}

object MYeelightDevice {
  def apply(deviceInfo: DeviceInfo)(implicit service: YeelightService): MYeelightDevice = {
    val state = new MYeelightState(deviceInfo.internalId, service)
    new MYeelightDevice(deviceInfo.internalId, state, service)
  }

}
