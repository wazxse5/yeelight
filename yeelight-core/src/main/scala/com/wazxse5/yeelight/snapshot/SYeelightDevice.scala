package com.wazxse5.yeelight.snapshot

import com.wazxse5.yeelight.valuetype.DeviceModel

case class SYeelightDevice(
  id: Option[String],
  model: Option[DeviceModel]
)
