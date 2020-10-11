package com.wazxse5.core

import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.ConnectionAdapter
import com.wazxse5.message.YeelightMessage

trait YeelightService {

  def devices: Set[YeelightDevice]

  def deviceInfo(internalId: InternalId): Option[DeviceInfo]

  def deviceOf(deviceInfo: DeviceInfo): YeelightDevice

  def deviceOf(address: String, port: Int = 55443): YeelightDevice

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(internalId: InternalId, command: YeelightCommand): Unit

  def handleMessage(message: YeelightMessage): Unit

  def connectionAdapter: ConnectionAdapter

  def exit: Int = connectionAdapter.exit

}
