package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.valuetype._
import org.joda.time.DateTime

class YeelightStateImpl(deviceId: String, yeelightService: YeelightService) extends YeelightState {
  
  private var _brightness = Property.empty[Brightness]
  private var _colorMode = Property.empty[ColorMode]
  private var _flowExpression = Property.empty[FlowExpression]
  private var _flowPower = Property.empty[FlowPower]
  private var _hue = Property.empty[Hue]
  private var _musicPower = Property.empty[MusicPower]
  private var _name = Property.empty[Name]
  private var _power = Property.empty[Power]
  private var _rgb = Property.empty[Rgb]
  private var _saturation = Property.empty[Saturation]
  private var _temperature = Property.empty[Temperature]
  private var _timerValue = Property.empty[TimerValue]

  private var _bgBrightness = Property.emptyBackground[Brightness]
  private var _bgColorMode = Property.emptyBackground[ColorMode]
  private var _bgFlowExpression = Property.emptyBackground[FlowExpression]
  private var _bgFlowPower = Property.emptyBackground[FlowPower]
  private var _bgHue = Property.emptyBackground[Hue]
  private var _bgPower = Property.emptyBackground[Power]
  private var _bgRgb = Property.emptyBackground[Rgb]
  private var _bgSaturation = Property.emptyBackground[Saturation]
  private var _bgTemperature = Property.emptyBackground[Temperature]

  private var _nlBrightness = Property.empty[Brightness]
  private var _activeMode = Property.empty[ActiveMode]

  private def all: Seq[Property[_]] = Seq(
    _brightness, _colorMode, _flowExpression, _flowPower, _hue, _musicPower, _name, _power,
    _rgb, _saturation, _temperature, _timerValue, _bgBrightness, _bgColorMode, _bgFlowExpression,
    _bgFlowPower, _bgHue, _bgPower, _bgRgb, _bgSaturation, _bgTemperature, _nlBrightness, _activeMode
  )

  override def brightness: Property[Brightness] = {
    _brightness = _brightness.withValue(yeelightService.deviceInfo(deviceId).brightness)
    _brightness
  }

  override def colorMode: Property[ColorMode] = {
    _colorMode = _colorMode.withValue(yeelightService.deviceInfo(deviceId).colorMode)
    _colorMode
  }

  override def flowExpression: Property[FlowExpression] = {
    _flowExpression = _flowExpression.withValue(yeelightService.deviceInfo(deviceId).flowExpression)
    _flowExpression
  }

  override def flowPower: Property[FlowPower] = {
    _flowPower = _flowPower.withValue(yeelightService.deviceInfo(deviceId).flowPower)
    _flowPower
  }

  override def hue: Property[Hue] = {
    _hue = _hue.withValue(yeelightService.deviceInfo(deviceId).hue)
    _hue
  }

  override def musicPower: Property[MusicPower] = {
    _musicPower = _musicPower.withValue(yeelightService.deviceInfo(deviceId).musicPower)
    _musicPower
  }

  override def name: Property[Name] = {
    _name = _name.withValue(yeelightService.deviceInfo(deviceId).name)
    _name
  }

  override def power: Property[Power] = {
    _power = _power.withValue(yeelightService.deviceInfo(deviceId).power)
    _power
  }

  override def rgb: Property[Rgb] = {
    _rgb = _rgb.withValue(yeelightService.deviceInfo(deviceId).rgb)
    _rgb
  }

  override def saturation: Property[Saturation] = {
    _saturation = _saturation.withValue(yeelightService.deviceInfo(deviceId).saturation)
    _saturation
  }

  override def temperature: Property[Temperature] = {
    _temperature = _temperature.withValue(yeelightService.deviceInfo(deviceId).temperature)
    _temperature
  }

  override def timerValue: Property[TimerValue] = {
    _timerValue = _timerValue.withValue(yeelightService.deviceInfo(deviceId).timerValue)
    _timerValue
  }

  override def bgBrightness: Property[Brightness] = {
    _bgBrightness = _bgBrightness.withValue(yeelightService.deviceInfo(deviceId).bgBrightness)
    _bgBrightness
  }

  override def bgColorMode: Property[ColorMode] = {
    _bgColorMode = _bgColorMode.withValue(yeelightService.deviceInfo(deviceId).bgColorMode)
    _bgColorMode
  }
  override def bgFlowExpression: Property[FlowExpression] = {
    _bgFlowExpression = _bgFlowExpression.withValue(yeelightService.deviceInfo(deviceId).bgFlowExpression)
    _bgFlowExpression
  }
  override def bgFlowPower: Property[FlowPower] = {
    _bgFlowPower = _bgFlowPower.withValue(yeelightService.deviceInfo(deviceId).bgFlowPower)
    _bgFlowPower
  }
  override def bgHue: Property[Hue] = {
    _bgHue = _bgHue.withValue(yeelightService.deviceInfo(deviceId).bgHue)
    _bgHue
  }
  override def bgPower: Property[Power] = {
    _bgPower = _bgPower.withValue(yeelightService.deviceInfo(deviceId).bgPower)
    _bgPower
  }
  override def bgRgb: Property[Rgb] = {
    _bgRgb = _bgRgb.withValue(yeelightService.deviceInfo(deviceId).bgRgb)
    _bgRgb
  }
  override def bgSaturation: Property[Saturation] = {
    _bgSaturation = _bgSaturation.withValue(yeelightService.deviceInfo(deviceId).bgSaturation)
    _bgSaturation
  }
  override def bgTemperature: Property[Temperature] = {
    _bgTemperature = _bgTemperature.withValue(yeelightService.deviceInfo(deviceId).bgTemperature)
    _bgTemperature
  }

  override def nlBrightness: Property[Brightness] = {
    _nlBrightness = _nlBrightness.withValue(yeelightService.deviceInfo(deviceId).nlBrightness)
    _nlBrightness
  }

  override def activeMode: Property[ActiveMode] = {
    _activeMode = _activeMode.withValue(yeelightService.deviceInfo(deviceId).activeMode)
    _activeMode
  }

  override def lastUpdate: DateTime = all.maxBy(_.lastUpdate).lastUpdate
}

object YeelightStateImpl {
  def apply(device: YeelightDevice): YeelightStateImpl = new YeelightStateImpl(device.deviceId, device.service)
}