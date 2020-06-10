package com.wazxse5.api.model

import com.wazxse5.api.InternalId
import com.wazxse5.api.command.YeelightCommand
import com.wazxse5.api.message.YeelightMessage
import com.wazxse5.core.{ConnectionAdapter, DeviceInfo}

trait IYeelightService {

  def devices: Set[IYeelightDevice]

  def deviceInfo(internalId: InternalId): Option[DeviceInfo]

  def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice

  def deviceOf(address: String, port: Int = 55443): IYeelightDevice

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(internalId: InternalId, command: YeelightCommand): Unit

  def handleMessage(message: YeelightMessage): Unit

  def connectionAdapter: ConnectionAdapter

}
