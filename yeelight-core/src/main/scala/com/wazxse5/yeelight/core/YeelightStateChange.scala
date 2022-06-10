package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.command.{SetBrightness, SetPower, SetTemperature, YeelightCommand}
import com.wazxse5.yeelight.api.valuetype.{Brightness, Hue, Power, Rgb, Saturation, Temperature}
import com.wazxse5.yeelight.core.message.{CommandResultMessage, DiscoveryResponseMessage, NotificationMessage}
import play.api.libs.json.JsValue

case class YeelightStateChange(
  isConnected: Option[Boolean] = None,
  power: Option[Power] = None,
  brightness: Option[Brightness] = None,
  temperature: Option[Temperature] = None,
  rgb: Option[Rgb] = None,
  hue: Option[Hue] = None,
  saturation: Option[Saturation] = None,
)

object YeelightStateChange {
  
  def fromDiscoveryResponse(message: DiscoveryResponseMessage): YeelightStateChange = {
    YeelightStateChange(
      power = Power.fromString(message.power),
      brightness = Brightness.fromString(message.brightness),
      temperature = Temperature.fromString(message.temperature),
      rgb = Rgb.fromString(message.rgb),
      hue = Hue.fromString(message.hue),
      saturation = Saturation.fromString(message.saturation),
    )
  }
  
  def fromNotification(message: NotificationMessage): YeelightStateChange = {
    def find[VT](name: String, fromJsValue: JsValue => Option[VT]): Option[VT] = {
      message.params.collectFirst {
        case (str, jsValue) if str == name => fromJsValue(jsValue)
      }.flatten
    }
    
    YeelightStateChange(
      power = find(Power.propFgName, Power.fromJsValue),
      brightness = find(Brightness.propFgName, Brightness.fromJsValue),
      temperature = find(Temperature.propFgName, Temperature.fromJsValue),
      rgb = find(Rgb.propFgName, Rgb.fromJsValue),
      hue = find(Hue.propFgName, Hue.fromJsValue),
      saturation = find(Saturation.propFgName, Saturation.fromJsValue),
    )
  }
  
  def fromCommandResult(command: YeelightCommand, resultMessage: CommandResultMessage): Option[YeelightStateChange] = {
    def isOk = resultMessage.result.isOk
    
    command match {
      case SetBrightness(brightness, _, _) if isOk => Some(YeelightStateChange(brightness = Some(brightness)))
      case SetTemperature(temperature, _, _) if isOk => Some(YeelightStateChange(temperature = Some(temperature)))
      case SetPower(power, _, _) if isOk => Some(YeelightStateChange(power = Some(power)))
      case _ => None
    }
  }
  
  def isConnected(newValue: Boolean): YeelightStateChange = {
    YeelightStateChange(isConnected = Some(newValue))
  }
  
}