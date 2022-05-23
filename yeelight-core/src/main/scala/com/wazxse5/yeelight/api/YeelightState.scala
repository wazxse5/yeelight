package com.wazxse5.yeelight.api

import com.wazxse5.yeelight.api.valuetype.{Brightness, Power, Temperature}

trait YeelightState {
  
  def isConnected: Boolean
  
  def power: Power
  
  def brightness: Brightness
  
  def temperature: Temperature
}
