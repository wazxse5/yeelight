package com.wazxse5.yeelight.api

import com.wazxse5.yeelight.api.valuetype.{Brightness, Hue, Power, Rgb, Saturation, Temperature}

trait YeelightState {
  
  def isConnected: Boolean
  
  def power: Power
  
  def brightness: Brightness
  
  def temperature: Temperature
  
  def rgb: Rgb
  
  def hue: Hue
  
  def saturation: Saturation
  
}
