package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.valuetype._
import org.joda.time.DateTime

class YeelightStateImpl(deviceId: String, yeelightService: YeelightService) extends YeelightState {
  private implicit def deviceIdImplicit: String = deviceId
  
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
    _brightness = _brightness.withValue(deviceInfo.brightness, deviceInfo.lastUpdate)
    _brightness
  }

  override def colorMode: Property[ColorMode] = {
    _colorMode = _colorMode.withValue(deviceInfo.colorMode, deviceInfo.lastUpdate)
    _colorMode
  }

  override def flowExpression: Property[FlowExpression] = {
    _flowExpression = _flowExpression.withValue(deviceInfo.flowExpression, deviceInfo.lastUpdate)
    _flowExpression
  }

  override def flowPower: Property[FlowPower] = {
    _flowPower = _flowPower.withValue(deviceInfo.flowPower, deviceInfo.lastUpdate)
    _flowPower
  }

  override def hue: Property[Hue] = {
    _hue = _hue.withValue(deviceInfo.hue, deviceInfo.lastUpdate)
    _hue
  }

  override def musicPower: Property[MusicPower] = {
    _musicPower = _musicPower.withValue(deviceInfo.musicPower, deviceInfo.lastUpdate)
    _musicPower
  }

  override def name: Property[Name] = {
    _name = _name.withValue(deviceInfo.name, deviceInfo.lastUpdate)
    _name
  }

  override def power: Property[Power] = {
    _power = _power.withValue(deviceInfo.power, deviceInfo.lastUpdate)
    _power
  }

  override def rgb: Property[Rgb] = {
    _rgb = _rgb.withValue(deviceInfo.rgb, deviceInfo.lastUpdate)
    _rgb
  }

  override def saturation: Property[Saturation] = {
    _saturation = _saturation.withValue(deviceInfo.saturation, deviceInfo.lastUpdate)
    _saturation
  }

  override def temperature: Property[Temperature] = {
    _temperature = _temperature.withValue(deviceInfo.temperature, deviceInfo.lastUpdate)
    _temperature
  }

  override def timerValue: Property[TimerValue] = {
    _timerValue = _timerValue.withValue(deviceInfo.timerValue, deviceInfo.lastUpdate)
    _timerValue
  }

  override def bgBrightness: Property[Brightness] = {
    _bgBrightness = _bgBrightness.withValue(deviceInfo.bgBrightness, deviceInfo.lastUpdate)
    _bgBrightness
  }

  override def bgColorMode: Property[ColorMode] = {
    _bgColorMode = _bgColorMode.withValue(deviceInfo.bgColorMode, deviceInfo.lastUpdate)
    _bgColorMode
  }
  override def bgFlowExpression: Property[FlowExpression] = {
    _bgFlowExpression = _bgFlowExpression.withValue(deviceInfo.bgFlowExpression, deviceInfo.lastUpdate)
    _bgFlowExpression
  }
  override def bgFlowPower: Property[FlowPower] = {
    _bgFlowPower = _bgFlowPower.withValue(deviceInfo.bgFlowPower, deviceInfo.lastUpdate)
    _bgFlowPower
  }
  override def bgHue: Property[Hue] = {
    _bgHue = _bgHue.withValue(deviceInfo.bgHue, deviceInfo.lastUpdate)
    _bgHue
  }
  override def bgPower: Property[Power] = {
    _bgPower = _bgPower.withValue(deviceInfo.bgPower, deviceInfo.lastUpdate)
    _bgPower
  }
  override def bgRgb: Property[Rgb] = {
    _bgRgb = _bgRgb.withValue(deviceInfo.bgRgb, deviceInfo.lastUpdate)
    _bgRgb
  }
  override def bgSaturation: Property[Saturation] = {
    _bgSaturation = _bgSaturation.withValue(deviceInfo.bgSaturation, deviceInfo.lastUpdate)
    _bgSaturation
  }
  override def bgTemperature: Property[Temperature] = {
    _bgTemperature = _bgTemperature.withValue(deviceInfo.bgTemperature, deviceInfo.lastUpdate)
    _bgTemperature
  }

  override def nlBrightness: Property[Brightness] = {
    _nlBrightness = _nlBrightness.withValue(deviceInfo.nlBrightness, deviceInfo.lastUpdate)
    _nlBrightness
  }

  override def activeMode: Property[ActiveMode] = {
    _activeMode = _activeMode.withValue(deviceInfo.activeMode, deviceInfo.lastUpdate)
    _activeMode
  }

  override def lastUpdate: DateTime = all.maxBy(_.lastUpdate).lastUpdate
  
  private def deviceInfo(implicit deviceId: String): DeviceInfo = {
    yeelightService.deviceInfo(deviceId)
  }
}

object YeelightStateImpl {
  def apply(device: YeelightDevice): YeelightStateImpl = new YeelightStateImpl(device.deviceId, device.service)
}