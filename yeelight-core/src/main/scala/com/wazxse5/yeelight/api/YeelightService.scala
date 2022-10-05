package com.wazxse5.yeelight.api

import com.wazxse5.yeelight.api.command.YeelightCommand
import com.wazxse5.yeelight.api.valuetype.DeviceModel

import java.util.{Map => JMap}
import java.lang.{String => JString}
import scala.jdk.CollectionConverters._


trait YeelightService {
  def devices: Map[String, YeelightDevice]
  
  def devicesJava: JMap[JString, YeelightDevice] = devices.asJava

  def addDevice(deviceId: String, model: DeviceModel, ip: String, port: Int): Unit
  
  def search(): Unit
  
  def startListening(): Unit
  
  def stopListening(): Unit
  
  def performCommand(deviceId: String, command: YeelightCommand): Unit
  
  def addEventListener(listener: YeelightEventListener): Unit
  
  def exit(): Unit
}
