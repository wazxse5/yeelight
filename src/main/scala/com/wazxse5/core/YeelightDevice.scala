package com.wazxse5.core

import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.NetworkLocation
import com.wazxse5.valuetype.DeviceModel

trait YeelightDevice {

  val internalId: InternalId

  def id: Option[String]

  def model: Option[DeviceModel]

  def firmwareVersion: Option[String]

  def supportedCommands: Option[Set[String]]

  def location: Option[NetworkLocation]

  def isConnected: Boolean

  def state: YeelightState

  def service: YeelightService

  def performCommand(command: YeelightCommand): Unit

}
