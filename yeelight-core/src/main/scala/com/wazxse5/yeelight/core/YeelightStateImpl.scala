package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.YeelightState
import com.wazxse5.yeelight.api.valuetype.{Brightness, Power, Temperature}

case class YeelightStateImpl(
  isConnected: Boolean,
  power: Power,
  brightness: Brightness,
  temperature: Temperature,
) extends YeelightState

object YeelightStateImpl {
  def empty: YeelightStateImpl = YeelightStateImpl(
    isConnected = false,
    power = Power.off,
    brightness = new Brightness(1),
    temperature = new Temperature(1700),
  )
}
