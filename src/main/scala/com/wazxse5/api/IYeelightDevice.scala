package com.wazxse5.api

import com.wazxse5.InternalId
import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.NetworkLocation
import com.wazxse5.valuetype.DeviceModel

trait IYeelightDevice { // TODO: Pomyśleć nad lepszą nazwą

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
