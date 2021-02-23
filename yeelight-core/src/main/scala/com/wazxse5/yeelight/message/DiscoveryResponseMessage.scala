package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.Json

import scala.util.Try

case class DiscoveryResponseMessage(
  header: String,
  cacheControl: String,
  date: String, // unimportant
  ext: String, // unimportant
  location: String,
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
) extends YeelightUnconnectedMessage {

  def locationAddress: String = location.split(':')(0)
  def locationPort: String = location.split(':')(1)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo("discoveryResponseMessage", Json.obj("text" -> text))

  override def text: String = {
    s"""|$header
        |Cache-Control: max-age=$cacheControl
        |Date: $date
        |Ext: $ext
        |Location: yeelight://$location
        |Server: $server
        |id: $deviceId
        |model: $model
        |fw_ver: $firmwareVersion
        |support: ${supportedCommands.mkString(" ")}
        |power: $power
        |bright: $brightness
        |color_mode: $colorMode
        |ct: $temperature
        |rgb: $rgb
        |hue: $hue
        |sat: $saturation
        |name: $name
        |""".stripMargin
  }
}

object DiscoveryResponseMessage {
  val defaultHeader = "HTTP/1.1 200 OK"
  val defaultServer = "POSIX UPnP/1.0 YGLC/1"

  def fromString(message: String): Option[DiscoveryResponseMessage] = {
    Try {
      val messageLines = message.linesIterator.toArray

      val header = messageLines(0)
      val cacheControl = messageLines(1).substring(23)
      val date = messageLines(2).substring(6)
      val ext = messageLines(3).substring(5)
      val location = messageLines(4).substring(21)
      val server = messageLines(5).substring(8)
      val id = messageLines(6).substring(4)
      val model = messageLines(7).substring(7)
      val firmwareVersion = messageLines(8).substring(8)
      val supportedCommands = messageLines(9).substring(9).split(' ').toSeq
      val power = messageLines(10).substring(7)
      val brightness = messageLines(11).substring(8)
      val colorMode = messageLines(12).substring(12)
      val temperature = messageLines(13).substring(4)
      val rgb = messageLines(14).substring(5)
      val hue = messageLines(15).substring(5)
      val saturation = messageLines(16).substring(5)
      val name = messageLines(17).substring(6)

      new DiscoveryResponseMessage(header, cacheControl, date, ext, location, server, id, model, firmwareVersion,
        supportedCommands, power, brightness, colorMode, temperature, rgb, hue, saturation, name
      )
    }.toOption
  }
}
