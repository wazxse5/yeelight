package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.Json

import scala.util.Try

case class DiscoveryResponseMessage(
  header: String,
  cacheControl: Int,
  date: String, // unimportant
  ext: String, // unimportant
  location: String,
  server: String, // unimportant
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
  text: String
) extends YeelightUnconnectedMessage {

  def locationAddress: String = location.split(':')(0)
  def locationPort: String = location.split(':')(1)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo("discoveryResponseMessage", Json.obj("text" -> text))
}

object DiscoveryResponseMessage {

  def fromString(message: String): Option[DiscoveryResponseMessage] = {
    Try {
      val messageLines = message.linesIterator.toArray

      val header = messageLines(0)
      val cacheControl = messageLines(1).substring(23).toInt
      val date = messageLines(2).substring(6)
      val ext = messageLines(3).substring(5)
      val location = messageLines(4).substring(21)
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

      new DiscoveryResponseMessage(header, cacheControl, date, ext, location, server, id, model, firmwareVersion,
        supportedCommands, power, brightness, colorMode, temperature, rgb, hue, saturation, name, message
      )
    }.toOption
  }
}
