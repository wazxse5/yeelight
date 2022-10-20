package com.wazxse5.yeelight.core.connection

import com.wazxse5.yeelight.api.valuetype._
import com.wazxse5.yeelight.core.message.{CommandResultMessageResult, DiscoveryResponseMessage, ResultGetProps, ResultOk}

case class FakeCaData(
  isListening: Boolean = false,
  devices: Map[String, FakeCaDevice],
  connectedDevicesIds: Set[String]
) {

  def isConnected(deviceId: String): Boolean =
    connectedDevicesIds.contains(deviceId)

  def exists(deviceId: String, ip: String, port: Int): Boolean =
    devices.get(deviceId).exists(d => d.ip == ip && d.port == port)

  def withConnected(deviceId: String): FakeCaData =
    copy(connectedDevicesIds = connectedDevicesIds + deviceId)

  def withDisconnected(deviceId: String): FakeCaData =
    copy(connectedDevicesIds = connectedDevicesIds - deviceId)

  def updated(
    deviceId: String,
    f: FakeCaDevice => (FakeCaDevice, CommandResultMessageResult)
  ): (FakeCaData, CommandResultMessageResult) = {
    devices.get(deviceId) match {
      case Some(device) =>
        val (updatedDevice, result) = f(device)
        copy(devices = devices + (device.deviceId -> updatedDevice)) -> result
      case None => ???
    }
  }
}

case class FakeCaDevice(
  deviceId: String,
  model: DeviceModel,
  firmwareVersion: String,
  supportedCommands: Seq[String],
  ip: String,
  port: Int,
  power: Power,
  brightness: Brightness,
  temperature: Temperature,
  rgb: Rgb,
  hue: Hue,
  saturation: Saturation,
) {

  private type UpdateResult = (FakeCaDevice, CommandResultMessageResult)

  def adjustBrightness(percent: Percent): UpdateResult = {
    val newBrightness = (brightness.value + percent.value).min(100).max(1)
    copy(brightness = Brightness(newBrightness)) -> ResultOk
  }

  def getProps(propertyNames: Seq[PropertyName]): UpdateResult = {
    this -> ResultGetProps(
      propertyNames.map {
        case PropertyName.brightness => brightness.value.toString
        case PropertyName.hue => hue.value.toString
        case PropertyName.power => power.value
        case PropertyName.rgb => rgb.value.toString
        case PropertyName.saturation => saturation.value.toString
        case PropertyName.temperature => temperature.value.toString
        case _ => ""
      }
    )
  }

  def setBrightness(value: Brightness): UpdateResult = {
    copy(brightness = value) -> ResultOk
  }

  def setHsv(value: Hue, saturation: Saturation): UpdateResult = {
    copy(hue = value, saturation = saturation) -> ResultOk
  }

  def setPower(value: Power): UpdateResult = {
    copy(power = value) -> ResultOk
  }

  def setRgb(value: Rgb): UpdateResult = {
    copy(rgb = value) -> ResultOk
  }

  def setTemperature(value: Temperature): UpdateResult = {
    copy(temperature = value) -> ResultOk
  }

  def toggle: UpdateResult = {
    copy(power = if (power == Power.on) Power.off else Power.on) -> ResultOk
  }

  def discoveryResponseMessage: DiscoveryResponseMessage = {
    DiscoveryResponseMessage(
      header = "HTTP/1.1 200 OK",
      cacheControl = "max-age=3600",
      address = ip,
      port = port,
      deviceId = deviceId,
      model = model.value,
      firmwareVersion = firmwareVersion,
      supportedCommands = supportedCommands,
      power = power.value,
      brightness = brightness.value.toString,
      colorMode = "???",
      temperature = temperature.value.toString,
      rgb = rgb.value.toString,
      hue = hue.value.toString,
      saturation = saturation.value.toString,
      name = ""
    )
  }
}
