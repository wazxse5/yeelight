package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.command.YeelightCommand
import com.wazxse5.yeelight.core.valuetype.DeviceModel

class YeelightDeviceImpl(
  val deviceId: String,
  val model: Option[DeviceModel],
  val firmwareVersion: Option[String],
  val supportedCommands: Option[Seq[String]],
  yeelightServiceImpl: YeelightServiceImpl,
) extends YeelightDevice {

  val state: YeelightStateImpl = new YeelightStateImpl()

  def performCommand(command: YeelightCommand): Unit = {
    yeelightServiceImpl.performCommand(deviceId, command)
  }
  
  def update(change: YeelightStateChange): Unit = {
    change.isConnected.foreach(state.isConnectedProperty.set)
    change.brightness.map(_.value).foreach(state.brightnessProperty.set)
    change.temperature.map(_.value).foreach(state.temperatureProperty.set)
  }

}
