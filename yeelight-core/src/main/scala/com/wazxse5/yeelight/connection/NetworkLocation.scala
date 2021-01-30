package com.wazxse5.yeelight.connection

import com.wazxse5.yeelight.connection.NetworkLocation.snapshotName
import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
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

  def apply(string: String): NetworkLocation = {
    val array = string.split(':')
    NetworkLocation(array(0), array(1).toInt)
  }

}
