package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.command.YeelightCommand
import com.wazxse5.yeelight.core.valuetype.DeviceModel

trait YeelightDevice {
  def deviceId: String
  
  def model: Option[DeviceModel]
  
  def firmwareVersion: Option[String]
  
  def supportedCommands: Option[Seq[String]]
  
  def performCommand(command: YeelightCommand): Unit
}
