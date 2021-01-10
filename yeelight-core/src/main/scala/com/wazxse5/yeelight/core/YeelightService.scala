package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.command.YeelightCommand
import com.wazxse5.yeelight.connection.ConnectionAdapter
import com.wazxse5.yeelight.message.Message

trait YeelightService {

  def devices: Set[YeelightDevice]

  def deviceInfo(internalId: InternalId): DeviceInfo

  def deviceOf(deviceInfo: DeviceInfo): YeelightDevice

  def deviceOf(address: String, port: Int = 55443): YeelightDevice

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(internalId: InternalId, command: YeelightCommand): Unit

  def handleMessage(message: Message): Unit

  def connectionAdapter: ConnectionAdapter

  def exit: Int = connectionAdapter.exit

}
