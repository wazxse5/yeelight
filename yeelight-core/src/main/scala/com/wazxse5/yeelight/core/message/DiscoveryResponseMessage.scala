package com.wazxse5.yeelight.core.message

import scala.util.Try

case class DiscoveryResponseMessage(
  header: String,
  cacheControl: String,
  date: String, // unimportant
  ext: String, // unimportant
  address: String,
  port: Int,
  server: String, // unimportant
  deviceId: String,
  model: String,
  firmwareVersion: String,
  supportedCommands: Seq[String],
  power: String,
  brightness: String,
  colorMode: String,
  temperature: String,
  rgb: String,
  hue: String,
  saturation: String,
  name: String,
) extends YeelightMessage

object DiscoveryResponseMessage {
  def fromString(message: String): Option[DiscoveryResponseMessage] = {
    Try {
      val messageLines = message.linesIterator.toArray
      new DiscoveryResponseMessage(
        header = messageLines(0),
        cacheControl = messageLines(1).substring(23),
        date = messageLines(2).substring(6),
        ext = messageLines(3).substring(5),
        address = messageLines(4).substring(21).split(':')(0),
        port = messageLines(4).substring(21).split(':')(1).toInt,
        server = messageLines(5).substring(8),
        deviceId = messageLines(6).substring(4),
        model = messageLines(7).substring(7),
        firmwareVersion = messageLines(8).substring(8),
        supportedCommands = messageLines(9).substring(9).split(' ').toSeq,
        power = messageLines(10).substring(7),
        brightness = messageLines(11).substring(8),
        colorMode = messageLines(12).substring(12),
        temperature = messageLines(13).substring(4),
        rgb = messageLines(14).substring(5),
        hue = messageLines(15).substring(5),
        saturation = messageLines(16).substring(5),
        name = messageLines(17).substring(6)
      )
    }.toOption
  }
}
