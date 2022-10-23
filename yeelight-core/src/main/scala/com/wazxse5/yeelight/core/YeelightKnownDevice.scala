package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.YeelightDevice
import com.wazxse5.yeelight.api.valuetype.DeviceModel

case class YeelightKnownDevice(
  deviceId: String,
  model: DeviceModel,
  firmwareVersion: String,
  supportedCommands: Seq[String],
  ip: String,
  port: Int
)

object YeelightKnownDevice {
  def fromYeelightDevice(device: YeelightDevice): YeelightKnownDevice = {
    YeelightKnownDevice(
      device.deviceId,
      device.model,
      device.firmwareVersion,
      device.supportedCommands,
      device.state.address,
      device.state.port
    )
  }
}
