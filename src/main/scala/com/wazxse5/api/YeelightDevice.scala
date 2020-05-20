package com.wazxse5.api

import com.wazxse5.InternalId
import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.NetworkLocation
import com.wazxse5.model.DeviceInfo
import com.wazxse5.valuetype.DeviceModel

class YeelightDevice private(
  val internalId: InternalId,
  val state: IYeelightState,
  val service: IYeelightService,
) extends IYeelightDevice {

  override def id: Option[String] = service.deviceInfo(internalId).flatMap(_.id)

  override def model: Option[DeviceModel] = service.deviceInfo(internalId).flatMap(_.model)

  override def location: Option[NetworkLocation] = service.deviceInfo(internalId).flatMap(_.location)

  override def firmwareVersion: Option[String] = service.deviceInfo(internalId).flatMap(_.firmwareVersion)

  override def supportedCommands: Option[Set[String]] = service.deviceInfo(internalId).flatMap(_.supportedCommands)

  override def isConnected: Boolean = service.deviceInfo(internalId).exists(_.isConnected)

  override def performCommand(command: YeelightCommand): Unit = service.performCommand(internalId, command)

}

object YeelightDevice {
  def apply(deviceInfo: DeviceInfo)(implicit service: IYeelightService): YeelightDevice = {
    val state = new YeelightState(deviceInfo.internalId, service)
    new YeelightDevice(deviceInfo.internalId, state, service)
  }

}
