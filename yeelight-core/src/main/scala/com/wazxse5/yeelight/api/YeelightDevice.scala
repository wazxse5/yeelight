package com.wazxse5.yeelight.api

import com.wazxse5.yeelight.api.command.YeelightCommand
import com.wazxse5.yeelight.api.valuetype.DeviceModel

trait YeelightDevice {
  def deviceId: String

  def model: DeviceModel

  def state: YeelightState

  def performCommand(command: YeelightCommand): Unit
}
