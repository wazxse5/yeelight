package com.wazxse5.api.message

import com.wazxse5.api.valuetype.{DeviceModel, Power}

case class AdvertisementMessage private (
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
  brightness: Int,
  colorMode: Int,
  temperature: Int,
  rgb: Int,
  hue: Int,
  saturation: Int,
  name: String,
  text: String
) extends DeviceInfoMessage {

  def headerCode: Int = header.substring(9, 12).toInt

  override def isValid: Boolean = { // TODO: Dorobić prawdziwą walidację
    header == "HTTP/1.1 200 OK" &&
      DeviceModel.names.contains(model) &&
      Power.values.contains(power)
  }
}

object AdvertisementMessage {

  def apply(message: String): AdvertisementMessage = {
    val messageLines = message.lines.toArray

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
    val brightness = messageLines(11).substring(8).toInt
    val colorMode = messageLines(12).substring(12).toInt
    val temperature = messageLines(13).substring(4).toInt
    val rgb = messageLines(14).substring(5).toInt
    val hue = messageLines(15).substring(5).toInt
    val saturation = messageLines(16).substring(5).toInt
    val name = messageLines(17).substring(6)

    new AdvertisementMessage(header, host, cacheControl, location, nts, server, id, model, firmwareVersion,
      supportedCommands, power, brightness, colorMode, temperature, rgb, hue, saturation, name, message
    )
  }
}
