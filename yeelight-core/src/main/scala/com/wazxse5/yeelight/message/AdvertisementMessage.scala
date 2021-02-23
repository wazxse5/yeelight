package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.Json

import scala.util.Try

case class AdvertisementMessage private(
  header: String,
  host: String,
  cacheControl: String,
  location: String,
  nts: String,
  server: String,
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
  name: String
) extends YeelightUnconnectedMessage {

  def locationAddress: String = location.split(':')(0)
  def locationPort: String = location.split(':')(1)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo("advertisementMessage", Json.obj("text" -> text))

  override def text: String = {
    s"""|$header
        |Host: $host
        |Cache-Control: max-age=$cacheControl
        |Location: yeelight://$location
        |NTS: $nts
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

object AdvertisementMessage {
  val defaultHeader = "NOTIFY * HTTP/1.1"
  val defaultServer = "POSIX UPnP/1.0 YGLC/1"

  def fromString(message: String): Option[AdvertisementMessage] = {
    Try {
      val messageLines = message.linesIterator.toArray

      val header = messageLines(0)
      val host = messageLines(1).substring(6)
      val cacheControl = messageLines(2).substring(23)
      val location = messageLines(3).substring(21)
      val nts = messageLines(4).substring(5)
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

      new AdvertisementMessage(header, host, cacheControl, location, nts, server, id, model, firmwareVersion,
        supportedCommands, power, brightness, colorMode, temperature, rgb, hue, saturation, name
      )
    }.toOption
  }
}
