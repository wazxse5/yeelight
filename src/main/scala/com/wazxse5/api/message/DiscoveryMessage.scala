package com.wazxse5.api.message
import play.api.libs.json.{JsNull, JsValue}

object DiscoveryMessage extends ApiUnconnectedMessage {

  override def json: JsValue = JsNull

  override val text: String = "M-SEARCH * HTTP/1.1\r\nHOST: 239.255.255.250:1982\r\nMAN: \"ssdp:discover\"\r\nST: wifi_bulb\r\n"

  override def isValid: Boolean = true
}
