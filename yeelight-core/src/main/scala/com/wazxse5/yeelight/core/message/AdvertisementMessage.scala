package com.wazxse5.yeelight.core.message

// TODO AdvertisementMessage
case class AdvertisementMessage() extends YeelightMessage

object AdvertisementMessage {
  def fromString(message: String): Option[AdvertisementMessage] = None
}