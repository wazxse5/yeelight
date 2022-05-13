package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.command.YeelightCommand
import javafx.collections.ObservableList

trait YeelightService {
  def devices: ObservableList[YeelightDevice]
  
  def search(): Unit
  
  def startListening(): Unit
  
  def stopListening(): Unit
  
  def performCommand(deviceId: String, command: YeelightCommand): Unit
  
  def exit(): Unit
}
