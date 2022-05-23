package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.{YeelightDevice, YeelightState}
import com.wazxse5.yeelight.api.command.YeelightCommand
import com.wazxse5.yeelight.api.valuetype.DeviceModel

class YeelightDeviceImpl(
  val deviceId: String,
  val model: DeviceModel,
  val firmwareVersion: String,
  val supportedCommands: Seq[String],
  yeelightServiceImpl: YeelightServiceImpl,
) extends YeelightDevice {

  private var _state: YeelightStateImpl = YeelightStateImpl.empty

  def state: YeelightState = _state

  def performCommand(command: YeelightCommand): Unit = {
    yeelightServiceImpl.performCommand(deviceId, command)
  }
  
  def update(change: YeelightStateChange): Unit = {
    _state = _state.copy(
      isConnected = change.isConnected.getOrElse(_state.isConnected),
      power = change.power.getOrElse(_state.power),
      brightness = change.brightness.getOrElse(_state.brightness),
      temperature = change.temperature.getOrElse(_state.temperature),
    )
  }
  
}
