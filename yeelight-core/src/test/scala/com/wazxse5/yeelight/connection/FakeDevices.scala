package com.wazxse5.yeelight.connection

import com.wazxse5.yeelight.core.DeviceInfo
import com.wazxse5.yeelight.valuetype.{Brightness, ColorMode, DeviceModel, FlowPower, Hue, IpAddress, MusicPower, Name, Port, Power, Rgb, Saturation, Temperature}

import scala.language.implicitConversions

object FakeDevices {
  def all = Seq(deviceColor1, deviceDesk2)

  val deviceColor1: DeviceInfo = DeviceInfo(
    deviceId = "device1",
    model = DeviceModel.color,
    firmwareVersion = "65",
    supportedCommands = Seq(
      "adjust_bright", "toggle", "set_adjust", "cron_get",
      "set_rgb", "cron_del", "set_name", "get_prop", "start_cf",
      "set_hsv", "set_bright", "set_scene", "cron_add", "adjust_color",
      "set_power", "set_ct_abx", "stop_cf", "adjust_ct", "set_music", "set_default"
    ),
    ipAddress = IpAddress("192.168.0.1"),
    port = Port.default,
    isConnected = false,
    brightness = Brightness(100),
    colorMode = ColorMode.temperature,
    flowExpression = None,
    flowPower = FlowPower.off,
    hue = Hue(270),
    musicPower = MusicPower.off,
    name = Name(""),
    power = Power.on,
    rgb = Rgb.red,
    saturation = Saturation(80),
    temperature = Temperature(3000),
    timerValue = None,
    None, None, None, None, None, None, None, None, None, None, None
  )

  val deviceDesk2: DeviceInfo = DeviceInfo(
    deviceId = "device2",
    model = DeviceModel.deskLamp,
    firmwareVersion = "62",
    supportedCommands = Seq(
      "adjust_bright", "cron_add", "toggle", "set_adjust", "cron_get", "cron_del",
      "set_name", "get_prop", "start_cf", "set_bright", "set_scene", "set_power",
      "set_ct_abx", "set_default", "stop_cf", "adjust_ct"
    ),
    ipAddress = IpAddress("192.168.0.2"),
    port = Port.default,
    isConnected = false,
    brightness = Brightness(20),
    colorMode = ColorMode.temperature,
    flowExpression = None,
    flowPower = FlowPower.off,
    hue = Hue(0),
    musicPower = MusicPower.off,
    name = Name(""),
    power = Power.on,
    rgb = Rgb(16777215),
    saturation = Saturation(0),
    temperature = Temperature(3000),
    timerValue = None,
    None, None, None, None, None, None, None, None, None, None, None
  )

  private implicit def toOpt[T](any: T): Option[T] = Some(any)
}
