package com.wazxse5.api

import com.wazxse5.InternalId
import com.wazxse5.command.YeelightCommand
import com.wazxse5.model.DeviceInfo

trait IYeelightService {

  def devices: Set[IYeelightDevice]

  def deviceInfo(internalId: InternalId): Option[DeviceInfo]

  def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice

  def deviceOf(address: String, port: Int = 55443): IYeelightDevice

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(internalId: InternalId, command: YeelightCommand): Unit
}
