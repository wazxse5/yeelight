package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.command.YeelightCommand
import com.wazxse5.yeelight.core.YeelightDevice.snapshotName
import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.yeelight.valuetype.{DeviceModel, IpAddress, Port}
import play.api.libs.json.Json

trait YeelightDevice extends Snapshotable {

  def deviceId: String

  def model: Option[DeviceModel]

  def firmwareVersion: Option[String]

  def supportedCommands: Option[Set[String]]

  def address: Option[IpAddress]

  def port: Option[Port]

  def isConnected: Boolean

  def state: YeelightState

  def service: YeelightService

  def performCommand(command: YeelightCommand): Unit

  override final def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, Json.obj(
      "deviceId" -> deviceId,
      DeviceModel.snapshotName -> model.map(_.snapshotInfo.value),
      "firmwareVersion" -> firmwareVersion,
      "supportedCommands" -> supportedCommands,
      IpAddress.snapshotName -> address.map(_.snapshotInfo.value),
      Port.snapshotName -> port.map(_.snapshotInfo.value),
      "isConnected" -> isConnected,
      state.snapshotInfo.pairw
    )
  )

}

object YeelightDevice {
  val snapshotName = "yeelightDevice"
}
