package com.wazxse5.api.message

import com.wazxse5.api.valuetype.{DeviceModel, Power}
import play.api.libs.json.{JsNull, JsValue}

case class DiscoveryResponseMessage private(
  header: String,
  cacheControl: Int,
  date: String = "",
  ext: String = "",
  location: String,
  server: String,
  deviceId: String,
  model: String,
  firmwareVersion: String,
  supportedCommands: Set[String],
  power: String,
  brightness: Int,
  colorMode: Int,
  temperature: Int,
  rgb: Int,
  hue: Int,
  saturation: Int,
  name: String,
  text: String
) extends YeelightUnconnectedMessage with DeviceInfoLike {

  def headerCode: Int = header.substring(9, 12).toInt

  override def json: JsValue = JsNull

  override def isValid: Boolean = { // TODO: Dorobić prawdziwą walidację
    header == "HTTP/1.1 200 OK" &&
      date == "" &&
      ext == "" &&
      DeviceModel.names.contains(model) &&
      Power.values.contains(power)
  }
}

object DiscoveryResponseMessage {

  def apply(message: String): DiscoveryResponseMessage = {
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
    val brightness = messageLines(11).substring(8).toInt
    val colorMode = messageLines(12).substring(12).toInt
    val temperature = messageLines(13).substring(4).toInt
    val rgb = messageLines(14).substring(5).toInt
    val hue = messageLines(15).substring(5).toInt
    val saturation = messageLines(16).substring(5).toInt
    val name = messageLines(17).substring(6)

    new DiscoveryResponseMessage(header, cacheControl, date, ext, location, server, id, model, firmwareVersion,
      supportedCommands, power, brightness, colorMode, temperature, rgb, hue, saturation, name, message
    )
  }
}
