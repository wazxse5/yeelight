package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.YeelightState
import com.wazxse5.yeelight.api.valuetype.{Brightness, Hue, Power, Rgb, Saturation, Temperature}

case class YeelightStateImpl(
  isConnected: Boolean,
  address: String,
  port: Int,
  power: Power,
  brightness: Brightness,
  temperature: Temperature,
  rgb: Rgb,
  hue: Hue,
  saturation: Saturation
) extends YeelightState

object YeelightStateImpl {
  val empty: YeelightStateImpl = YeelightStateImpl(
    isConnected = false,
    address = "unknown",
    port = -1,
    power = Power.off,
    brightness = Brightness(1),
    temperature = Temperature(1700),
    rgb = Rgb(1),
    hue = Hue(0),
    saturation = Saturation(0)
  )
}
