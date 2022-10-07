package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.valuetype.DeviceModel

case class YeelightKnownDevice(
  deviceId: String,
  model: DeviceModel,
  firmwareVersion: String,
  supportedCommands: Seq[String],
  ip: String,
  port: Int
)
