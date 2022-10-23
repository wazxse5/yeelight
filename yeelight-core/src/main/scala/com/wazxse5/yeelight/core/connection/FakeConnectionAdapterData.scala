package com.wazxse5.yeelight.core.connection

import com.wazxse5.yeelight.api.valuetype._
import com.wazxse5.yeelight.core.message.{CommandResultMessageResult, DiscoveryResponseMessage, ResultGetProps, ResultOk}
import play.api.libs.json.JsValue

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
    f: FakeCaDevice => (FakeCaDevice, CommandResultMessageResult, Map[PropertyName, JsValue])
  ): (FakeCaData, CommandResultMessageResult, Map[PropertyName, JsValue]) = {
    devices.get(deviceId) match {
      case Some(device) =>
        val (updatedDevice, commandResult, changesToNotify) = f(device)
        (copy(devices = devices + (device.deviceId -> updatedDevice)), commandResult, changesToNotify)
      case None => ???
    }
  }
}

object FakeCaData {
  val empty: FakeCaData = FakeCaData(isListening = false, Map.empty, Set.empty)
}

case class FakeCaDevice(
  deviceId: String,
  model: DeviceModel,
  firmwareVersion: String,
  supportedCommands: Seq[String],
  ip: String,
  port: Int,
  brightness: Brightness,
  hue: Hue,
  power: Power,
  rgb: Rgb,
  saturation: Saturation,
  temperature: Temperature,
) {

  private type UpdateResult = (FakeCaDevice, CommandResultMessageResult, Map[PropertyName, JsValue])

  def adjustBrightness(percent: Percent): UpdateResult = {
    val newBrightness = Brightness((brightness.value + percent.value).min(100).max(1))
    val changesMap = Map(PropertyName.brightness -> brightness.paramValue)
    (copy(brightness = newBrightness), ResultOk, changesMap)
  }

  def getProps(propertyNames: Seq[PropertyName]): UpdateResult = {
    val result = ResultGetProps(
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
    (this, result, Map.empty)
  }

  def setBrightness(value: Brightness): UpdateResult = {
    (copy(brightness = value), ResultOk, Map(PropertyName.brightness -> value.paramValue))
  }

  def setHsv(value: Hue, saturation: Saturation): UpdateResult = {
    val changesMap = Map(PropertyName.hue -> hue.paramValue,PropertyName.saturation -> saturation.paramValue)
    (copy(hue = value, saturation = saturation), ResultOk, changesMap)
  }

  def setPower(value: Power): UpdateResult = {
    (copy(power = value), ResultOk, Map(PropertyName.power -> value.paramValue))
  }

  def setRgb(value: Rgb): UpdateResult = {
    (copy(rgb = value), ResultOk, Map(PropertyName.rgb -> value.paramValue))
  }

  def setTemperature(value: Temperature): UpdateResult = {
    (copy(temperature = value), ResultOk, Map(PropertyName.temperature -> value.paramValue))
  }

  def toggle: UpdateResult = {
    val newValue = if (power == Power.on) Power.off else Power.on
    (copy(power = newValue), ResultOk, Map(PropertyName.power -> newValue.paramValue))
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
