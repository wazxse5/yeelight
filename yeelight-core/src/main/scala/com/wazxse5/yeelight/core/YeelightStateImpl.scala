package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.YeelightState
import com.wazxse5.yeelight.api.valuetype.{Brightness, Hue, Power, Rgb, Saturation, Temperature}

case class YeelightStateImpl(
  isConnected: Boolean,
  power: Power,
  brightness: Brightness,
  temperature: Temperature,
  rgb: Rgb,
  hue: Hue,
  saturation: Saturation
) extends YeelightState

object YeelightStateImpl {
  def empty: YeelightStateImpl = YeelightStateImpl(
    isConnected = false,
    power = Power.off,
    brightness = Brightness(1),
    temperature = Temperature(1700),
    rgb = Rgb(0, 0, 0),
    hue = Hue(0),
    saturation = Saturation(0)
  )
}
