package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.command._
import com.wazxse5.yeelight.api.valuetype._
import com.wazxse5.yeelight.core.message.{CommandResultMessage, DiscoveryResponseMessage, NotificationMessage}
import play.api.libs.json.{JsArray, JsString}

case class YeelightStateChange(
  isConnected: Option[Boolean] = None,
  address: Option[String] = None,
  port: Option[Int] = None,
  firmwareVersion: Option[String] = None,
  supportedCommands: Option[Seq[String]] = None,
  brightness: Option[Brightness] = None,
  hue: Option[Hue] = None,
  power: Option[Power] = None,
  rgb: Option[Rgb] = None,
  saturation: Option[Saturation] = None,
  temperature: Option[Temperature] = None,
) {
  def containsChanges: Boolean = productIterator.exists {
    case option: Option[_] => option.isDefined
  }
}

object YeelightStateChange {

  val empty: YeelightStateChange = YeelightStateChange()
  
  def fromDiscoveryResponse(message: DiscoveryResponseMessage): YeelightStateChange = {
    YeelightStateChange(
      firmwareVersion = Some(message.firmwareVersion),
      supportedCommands = Some(message.supportedCommands),
      power = Power.fromString(message.power),
      brightness = Brightness.fromString(message.brightness),
      temperature = Temperature.fromString(message.temperature),
      rgb = Rgb.fromString(message.rgb),
      hue = Hue.fromString(message.hue),
      saturation = Saturation.fromString(message.saturation),
    )
  }

  def fromNotification(message: NotificationMessage): YeelightStateChange = {
    val propertyValueMap = message.params
      .map { case (p, v) => PropertyName.fromString(p) -> v }
      .collect { case (Some(p), v)  => p -> v}

    YeelightStateChange(
      brightness = propertyValueMap.get(PropertyName.brightness).flatMap(Brightness.fromJsValue),
      hue = propertyValueMap.get(PropertyName.hue).flatMap(Hue.fromJsValue),
      power = propertyValueMap.get(PropertyName.power).flatMap(Power.fromJsValue),
      rgb = propertyValueMap.get(PropertyName.rgb).flatMap(Rgb.fromJsValue),
      saturation = propertyValueMap.get(PropertyName.saturation).flatMap(Saturation.fromJsValue),
      temperature = propertyValueMap.get(PropertyName.temperature).flatMap(Temperature.fromJsValue),
    )
  }
  
  def fromCommandResult(command: YeelightCommand, resultMessage: CommandResultMessage): YeelightStateChange = {
    val result = resultMessage.result

    command match {
      case GetProps(propertyNames) =>
        val propertyValues = result.value.asInstanceOf[JsArray].value.map(_.asInstanceOf[JsString].value)
        val propertyValueMap = propertyNames.zip(propertyValues).toMap
        YeelightStateChange(
          brightness = propertyValueMap.get(PropertyName.brightness).flatMap(Brightness.fromString),
          hue = propertyValueMap.get(PropertyName.hue).flatMap(Hue.fromString),
          power = propertyValueMap.get(PropertyName.power).flatMap(Power.fromString),
          rgb = propertyValueMap.get(PropertyName.rgb).flatMap(Rgb.fromString),
          saturation = propertyValueMap.get(PropertyName.saturation).flatMap(Saturation.fromString),
          temperature = propertyValueMap.get(PropertyName.temperature).flatMap(Temperature.fromString),
        )
      case SetBrightness(brightness, _, _) if result.isOk => YeelightStateChange(brightness = Some(brightness))
      case SetHsv(hue, saturation, _, _) if result.isOk => YeelightStateChange(hue = Some(hue), saturation = Some(saturation))
      case SetPower(power, _, _) if result.isOk => YeelightStateChange(power = Some(power))
      case SetRgb(rgb, _, _) if result.isOk => YeelightStateChange(rgb = Some(rgb))
      case SetTemperature(temperature, _, _) if result.isOk => YeelightStateChange(temperature = Some(temperature))
      case _ => empty
    }
  }
  
  def isConnected(newValue: Boolean, address: Option[String] = None, port: Option[Int] = None): YeelightStateChange = {
    YeelightStateChange(
      isConnected = Some(newValue),
      address = address,
      port = port
    )
  }
  
}