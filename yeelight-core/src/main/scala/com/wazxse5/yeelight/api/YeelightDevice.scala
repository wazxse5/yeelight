package com.wazxse5.yeelight.api

import com.wazxse5.yeelight.api.command.YeelightCommand
import com.wazxse5.yeelight.api.valuetype.DeviceModel

import java.lang.{String => JString}
import java.util.{List => JList}
import scala.jdk.CollectionConverters._

trait YeelightDevice {
  def deviceId: String
  
  def model: DeviceModel
  
  def firmwareVersion: String
  
  def supportedCommands: Seq[String]
  
  def supportedCommandsJava: JList[JString] = supportedCommands.asJava
  
  def state: YeelightState
  
  def performCommand(command: YeelightCommand): Unit
}
