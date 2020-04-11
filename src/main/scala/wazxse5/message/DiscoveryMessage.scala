package wazxse5.message

object DiscoveryMessage extends ApiMessage {

  override val text: String = "M-SEARCH * HTTP/1.1\r\nHOST: 239.255.255.250:1982\r\nMAN: \"ssdp:discover\"\r\nST: wifi_bulb\r\n"

  override def isValid: Boolean = true
}
