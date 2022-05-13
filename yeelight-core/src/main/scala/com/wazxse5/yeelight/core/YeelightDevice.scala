package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.command.YeelightCommand
import com.wazxse5.yeelight.core.valuetype.DeviceModel

trait YeelightDevice {
  def deviceId: String
  
  def model: DeviceModel
  
  def firmwareVersion: Option[String]
  
  def supportedCommands: Option[Seq[String]]
  
  def state: YeelightState
  
  def performCommand(command: YeelightCommand): Unit
}
