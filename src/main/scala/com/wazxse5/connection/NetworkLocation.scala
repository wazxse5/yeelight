package com.wazxse5.connection

import com.wazxse5.connection.NetworkLocation.snapshotName
import com.wazxse5.snapshot.{SnapshotInfo, Snapshotable}
import play.api.libs.json.Json

case class NetworkLocation(address: String, port: Int) extends Snapshotable {
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, Json.obj(
      "address" -> address,
      "port" -> port
    )
  )
}

object NetworkLocation {
  val snapshotName = "networkLocation"
}
