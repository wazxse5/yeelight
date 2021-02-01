package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.Json

import scala.util.Try

case class AdvertisementMessage private(
  header: String,
  host: String,
  cacheControl: Int,
  location: String,
  nts: String,
  server: String,
  deviceId: String,
  model: String,
  firmwareVersion: String,
  supportedCommands: Set[String],
  power: String,
  brightness: String,
  colorMode: String,
  temperature: String,
  rgb: String,
  hue: String,
  saturation: String,
  name: String,
  override val text: String
) extends YeelightUnconnectedMessage {

  def locationAddress: String = location.split(':')(0)
  def locationPort: String = location.split(':')(1)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo("advertisementMessage", Json.obj("text" -> text))
}

object AdvertisementMessage {

  def fromString(message: String): Option[AdvertisementMessage] = {
    Try {
      val messageLines = message.linesIterator.toArray

      val header = messageLines(0)
      val host = messageLines(1).substring(6)
      val cacheControl = messageLines(2).substring(23).toInt
      val location = messageLines(3).substring(21)
      val nts = messageLines(4).substring(5)
      val server = messageLines(5).substring(8)
      val id = messageLines(6).substring(6)
      val model = messageLines(7).substring(7)
      val firmwareVersion = messageLines(8).substring(8)
      val supportedCommands = messageLines(9).substring(9).split(' ').toSet
      val power = messageLines(10).substring(7)
      val brightness = messageLines(11).substring(8)
      val colorMode = messageLines(12).substring(12)
      val temperature = messageLines(13).substring(4)
      val rgb = messageLines(14).substring(5)
      val hue = messageLines(15).substring(5)
      val saturation = messageLines(16).substring(5)
      val name = messageLines(17).substring(6)

      new AdvertisementMessage(header, host, cacheControl, location, nts, server, id, model, firmwareVersion,
        supportedCommands, power, brightness, colorMode, temperature, rgb, hue, saturation, name, message
      )
    }.toOption
  }
}
