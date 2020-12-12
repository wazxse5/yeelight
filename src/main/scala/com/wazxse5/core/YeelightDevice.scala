package com.wazxse5.core

import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.NetworkLocation
import com.wazxse5.core.YeelightDevice.snapshotName
import com.wazxse5.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.valuetype.DeviceModel
import play.api.libs.json.Json

trait YeelightDevice extends Snapshotable {

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

  override final def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, Json.obj(
      internalId.snapshotInfo.pairw,
      "id" -> id,
      DeviceModel.snapshotName -> model.map(_.snapshotInfo.value),
      "firmwareVersion" -> firmwareVersion,
      "supportedCommands" -> supportedCommands,
      NetworkLocation.snapshotName -> location.map(_.snapshotInfo.value),
      "isConnected" -> isConnected,
      state.snapshotInfo.pairw
    )
  )

}

object YeelightDevice {
  val snapshotName = "yeelightDevice"
}
