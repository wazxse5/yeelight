package com.wazxse5.yeelight.testkit

import com.wazxse5.yeelight.core.DeviceInfo

case class FakeDevice(
  deviceInfo: DeviceInfo,
  default: Option[DeviceInfo]
)
