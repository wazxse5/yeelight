package com.wazxse5.api.model

import com.wazxse5.api.InternalId
import com.wazxse5.api.command.YeelightCommand
import com.wazxse5.api.valuetype.DeviceModel
import com.wazxse5.core.connection.NetworkLocation

trait IYeelightDevice {

  val internalId: InternalId

  def id: Option[String]

  def model: Option[DeviceModel]

  def firmwareVersion: Option[String]

  def supportedCommands: Option[Set[String]]

  def location: Option[NetworkLocation]

  def isConnected: Boolean

  def state: IYeelightState

  def service: IYeelightService

  def performCommand(command: YeelightCommand): Unit

}
