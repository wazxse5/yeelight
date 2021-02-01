package com.wazxse5.yeelight.message
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.JsNull

object DiscoveryMessage extends YeelightUnconnectedMessage {

  override val text: String = "M-SEARCH * HTTP/1.1\r\nHOST: 239.255.255.250:1982\r\nMAN: \"ssdp:discover\"\r\nST: wifi_bulb\r\n"

  override def snapshotInfo: SnapshotInfo = SnapshotInfo("discoveryMessage", JsNull)
}
