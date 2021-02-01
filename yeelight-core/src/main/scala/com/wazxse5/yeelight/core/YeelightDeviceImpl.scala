package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.command.YeelightCommand
import com.wazxse5.yeelight.valuetype.{DeviceModel, IpAddress, Port}

class YeelightDeviceImpl private(
  val deviceId: String,
  val state: YeelightState,
  val service: YeelightService,
) extends YeelightDevice {

  override def model: Option[DeviceModel] = service.deviceInfo(deviceId).model

  override def address: Option[IpAddress] = service.deviceInfo(deviceId).ipAddress

  override def port: Option[Port] = service.deviceInfo(deviceId).port

  override def firmwareVersion: Option[String] = service.deviceInfo(deviceId).firmwareVersion

  override def supportedCommands: Option[Set[String]] = service.deviceInfo(deviceId).supportedCommands

  override def isConnected: Boolean = service.deviceInfo(deviceId).isConnected

  override def performCommand(command: YeelightCommand): Unit = service.performCommand(deviceId, command)

}

object YeelightDeviceImpl {
  def apply(deviceInfo: DeviceInfo)(implicit service: YeelightService): YeelightDeviceImpl = {
    val state = new YeelightStateImpl(deviceInfo.deviceId, service)
    new YeelightDeviceImpl(deviceInfo.deviceId, state, service)
  }

}
