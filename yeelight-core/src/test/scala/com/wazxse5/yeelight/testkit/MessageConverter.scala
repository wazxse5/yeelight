package com.wazxse5.yeelight.testkit

import com.wazxse5.yeelight.core.{DeviceInfo, DeviceInfoChange}
import com.wazxse5.yeelight.message.{DiscoveryResponseMessage, NotificationMessage}
import com.wazxse5.yeelight.valuetype._
import play.api.libs.json.Json

object MessageConverter {
  def deviceInfoToDiscoveryResponse(deviceInfo: DeviceInfo): DiscoveryResponseMessage = {
    DiscoveryResponseMessage(
      "HTTP/1.1 200 OK",
      "3600",
      "",
      "",
      s"${deviceInfo.ipAddress.get.strValue}:${deviceInfo.port.get.strValue}",
      "POSIX UPnP/1.0 YGLC/1",
      deviceInfo.deviceId,
      deviceInfo.model.get.strValue,
      deviceInfo.firmwareVersion.get,
      deviceInfo.supportedCommands.get,
      deviceInfo.power.get.strValue,
      deviceInfo.brightness.get.strValue,
      deviceInfo.colorMode.get.strValue,
      deviceInfo.temperature.get.strValue,
      deviceInfo.rgb.get.strValue,
      deviceInfo.hue.get.strValue,
      deviceInfo.saturation.get.strValue,
      deviceInfo.name.get.strValue
    )
  }

  def deviceInfoChangeToNotification(deviceId: String, deviceInfoChange: DeviceInfoChange): NotificationMessage = {
    val brightnessChange = Brightness.propFgName -> deviceInfoChange.brightness.toOption
    val colorModeChange = ColorMode.propFgName -> deviceInfoChange.colorMode.toOption
    val flowExpressionChange = FlowExpression.propFgName -> deviceInfoChange.flowExpression.toOption
    val flowPowerChange = FlowPower.propFgName -> deviceInfoChange.flowPower.toOption
    val hueChange = Hue.propFgName -> deviceInfoChange.hue.toOption
    val musicPowerChange = MusicPower.propFgName -> deviceInfoChange.musicPower.toOption
    val nameChange = Name.propFgName -> deviceInfoChange.name.toOption
    val powerChange = Power.propFgName -> deviceInfoChange.power.toOption
    val rgbChange = Rgb.propFgName -> deviceInfoChange.rgb.toOption
    val saturationChange = Saturation.propFgName -> deviceInfoChange.saturation.toOption
    val temperatureChange = Temperature.propFgName -> deviceInfoChange.temperature.toOption
    val timerValueChange = TimerValue.propFgName -> deviceInfoChange.timerValue.toOption
    val bgBrightnessChange = Brightness.propBgName -> deviceInfoChange.bgBrightness.toOption
    val bgColorModeChange = ColorMode.propBgName -> deviceInfoChange.bgColorMode.toOption
    val bgFlowExpressionChange = FlowExpression.propBgName -> deviceInfoChange.bgFlowExpression.toOption
    val bgFlowPowerChange = FlowPower.propBgName -> deviceInfoChange.bgFlowPower.toOption
    val bgHueChange = Hue.propBgName -> deviceInfoChange.bgHue.toOption
    val bgPowerChange = Power.propBgName -> deviceInfoChange.bgPower.toOption
    val bgRgbChange = Rgb.propBgName -> deviceInfoChange.bgRgb.toOption
    val bgSaturationChange = Saturation.propBgName -> deviceInfoChange.bgSaturation.toOption
    val bgTemperatureChange = Temperature.propBgName -> deviceInfoChange.bgTemperature.toOption
    val nlBrightnessChange = Brightness.propNlName -> deviceInfoChange.nlBrightness.toOption
    val activeModeChange = ActiveMode.propFgName -> deviceInfoChange.activeMode.toOption

    val changesOpts: Seq[(String, Option[PropValueType[_]])] = Seq(
      brightnessChange, colorModeChange, flowExpressionChange, flowPowerChange, hueChange, musicPowerChange,
      nameChange, powerChange, rgbChange, saturationChange, temperatureChange, timerValueChange, bgBrightnessChange,
      bgColorModeChange, bgFlowExpressionChange, bgFlowPowerChange, bgHueChange, bgPowerChange, bgRgbChange,
      bgSaturationChange, bgTemperatureChange, nlBrightnessChange, activeModeChange
    )
    val changes = changesOpts.collect { case (propName, Some(change)) => propName -> change.strValue }.toMap
    val json = Json.obj("method" -> "props", "params" -> changes)
    NotificationMessage(deviceId, json)
  }


}
