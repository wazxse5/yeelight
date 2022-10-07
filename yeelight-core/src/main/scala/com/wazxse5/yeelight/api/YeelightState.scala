package com.wazxse5.yeelight.api

import com.wazxse5.yeelight.api.valuetype._

trait YeelightState {

  def isConnected: Boolean

  def address: String

  def port: Int

  def power: Power

  def brightness: Brightness

  def temperature: Temperature

  def rgb: Rgb

  def hue: Hue

  def saturation: Saturation

}
