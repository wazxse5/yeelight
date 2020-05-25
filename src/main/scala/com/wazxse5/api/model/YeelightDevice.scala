package com.wazxse5.api.model

import com.wazxse5.api.InternalId
import com.wazxse5.api.command.YeelightCommand
import com.wazxse5.api.valuetype.DeviceModel
import com.wazxse5.core.DeviceInfo
import com.wazxse5.core.connection.NetworkLocation

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
