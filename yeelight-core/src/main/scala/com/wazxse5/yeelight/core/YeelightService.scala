package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.command.YeelightCommand

import scala.jdk.CollectionConverters._

trait YeelightService {
  def devices: Seq[YeelightDevice]
  
  def devicesJava: java.util.List[YeelightDevice] = devices.asJava
  
  def search(): Unit
  
  def startListening(): Unit
  
  def stopListening(): Unit
  
  def performCommand(deviceId: String, command: YeelightCommand): Unit
  
  def exit(): Unit
}
