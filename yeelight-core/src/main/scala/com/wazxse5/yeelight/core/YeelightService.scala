package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.command.YeelightCommand
import com.wazxse5.yeelight.connection.ConnectionAdapter
import com.wazxse5.yeelight.message.Message

trait YeelightService {

  def devices: Set[YeelightDevice]

  def deviceInfo(deviceId: String): DeviceInfo

  def deviceOf(deviceInfo: DeviceInfo): YeelightDevice

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(deviceId: String, command: YeelightCommand): Unit

  def handleMessage(message: Message): Unit

  def connectionAdapter: ConnectionAdapter

  def exit: Int = connectionAdapter.exit

}
