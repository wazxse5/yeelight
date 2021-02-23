package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.message._
import com.wazxse5.yeelight.valuetype._
import play.api.libs.json.{JsString, JsValue}

import scala.language.implicitConversions

case class DeviceInfoChange(
  model: Change[DeviceModel] = Keep,
  firmwareVersion: Change[String] = Keep,
  supportedCommands: Change[Seq[String]] = Keep,
  //
  brightness: Change[Brightness] = Keep,
  colorMode: Change[ColorMode] = Keep,
  flowExpression: Change[FlowExpression] = Keep,
  flowPower: Change[FlowPower] = Keep,
  hue: Change[Hue] = Keep,
  musicPower: Change[MusicPower] = Keep,
  name: Change[Name] = Keep,
  power: Change[Power] = Keep,
  rgb: Change[Rgb] = Keep,
  saturation: Change[Saturation] = Keep,
  temperature: Change[Temperature] = Keep,
  timerValue: Change[TimerValue] = Keep,
  //
  bgBrightness: Change[Brightness] = Keep,
  bgColorMode: Change[ColorMode] = Keep,
  bgFlowExpression: Change[FlowExpression] = Keep,
  bgFlowPower: Change[FlowPower] = Keep,
  bgHue: Change[Hue] = Keep,
  bgPower: Change[Power] = Keep,
  bgRgb: Change[Rgb] = Keep,
  bgSaturation: Change[Saturation] = Keep,
  bgTemperature: Change[Temperature] = Keep,
  //
  nlBrightness: Change[Brightness] = Keep,
  activeMode: Change[ActiveMode] = Keep
)

object DeviceInfoChange {
  def fromDiscoveryResponse(message: DiscoveryResponseMessage): DeviceInfoChange = {
    DeviceInfoChange(
      model = DeviceModel.fromString(message.model),
      firmwareVersion = Modify(message.firmwareVersion),
      supportedCommands = Modify(message.supportedCommands),
      //
      brightness = Brightness.fromString(message.brightness),
      colorMode = ColorMode.fromString(message.colorMode),
      hue = Hue.fromString(message.hue),
      name = Name.fromString(message.name),
      power = Power.fromString(message.power),
      rgb = Rgb.fromString(message.rgb),
      saturation = Saturation.fromString(message.saturation),
      temperature = Temperature.fromString(message.temperature),
    )
  }

  def fromAdvertisement(message: AdvertisementMessage): DeviceInfoChange = {
    DeviceInfoChange(
      model = DeviceModel.fromString(message.model),
      firmwareVersion = Modify(message.firmwareVersion),
      supportedCommands = Modify(message.supportedCommands),
      //
      brightness = Brightness.fromString(message.brightness),
      colorMode = ColorMode.fromString(message.colorMode),
      hue = Hue.fromString(message.hue),
      name = Name.fromString(message.name),
      power = Power.fromString(message.power),
      rgb = Rgb.fromString(message.rgb),
      saturation = Saturation.fromString(message.saturation),
      temperature = Temperature.fromString(message.temperature),
    )
  }

  def fromGetPropsCommandResult(command: CommandMessage, result: CommandResultMessage): DeviceInfoChange = {
    val propsNames = command.arguments.map(_.asInstanceOf[JsString]).map(_.value)
    val propsResults = result.result.getOrElse(Seq.empty)
    val pairs = propsNames.zip(propsResults).filter(_._2.nonEmpty)

    def find[VT <: ValueType[_]](name: String, str2VT: String => Option[VT]): Change[VT] = {
      pairs.collectFirst {
        case (str, jsValue) if str == name => str2VT(jsValue)
      }.flatten match {
        case Some(value) => Modify(value)
        case None => Keep
      }
    }

    DeviceInfoChange(
      brightness = find(Brightness.propFgName, Brightness.fromString),
      colorMode = find(ColorMode.propFgName, ColorMode.fromString),
      flowExpression = find(FlowExpression.propFgName, FlowExpression.fromString),
      flowPower = find(FlowPower.propFgName, FlowPower.fromString),
      hue = find(Hue.propFgName, Hue.fromString),
      musicPower = find(MusicPower.propFgName, MusicPower.fromString),
      name = find(Name.propFgName, Name.fromString),
      power = find(Power.propFgName, Power.fromString),
      rgb = find(Rgb.propFgName, Rgb.fromString),
      saturation = find(Saturation.propFgName, Saturation.fromString),
      temperature = find(Temperature.propFgName, Temperature.fromString),
      timerValue = find(TimerValue.propFgName, TimerValue.fromString),
      //
      bgBrightness = find(Brightness.propBgName, Brightness.fromString),
      bgColorMode = find(ColorMode.propBgName, ColorMode.fromString),
      bgFlowExpression = find(FlowExpression.propBgName, FlowExpression.fromString),
      bgFlowPower = find(FlowPower.propBgName, FlowPower.fromString),
      bgHue = find(Hue.propBgName, Hue.fromString),
      bgPower = find(Power.propBgName, Power.fromString),
      bgRgb = find(Rgb.propBgName, Rgb.fromString),
      bgSaturation = find(Saturation.propBgName, Saturation.fromString),
      bgTemperature = find(Temperature.propBgName, Temperature.fromString),
      //
      nlBrightness = find(Brightness.propNlName, Brightness.fromString),
      activeMode = find(ActiveMode.propFgName, ActiveMode.fromString)
    )
  }

  def fromNotification(message: NotificationMessage): DeviceInfoChange = {
    def find[VT <: ValueType[_]](name: String, json2VT: JsValue => Option[VT]): Change[VT] = {
      message.params.toSeq.collectFirst {
        case (str, jsValue) if str == name => json2VT(jsValue)
      }.flatten match {
        case Some(value) => Modify(value)
        case None => Keep
      }
    }

    DeviceInfoChange(
      brightness = find(Brightness.propFgName, Brightness.fromJsValue),
      colorMode = find(ColorMode.propFgName, ColorMode.fromJsValue),
      flowExpression = find(FlowExpression.propFgName, FlowExpression.fromJsValue),
      flowPower = find(FlowPower.propFgName, FlowPower.fromJsValue),
      hue = find(Hue.propFgName, Hue.fromJsValue),
      musicPower = find(MusicPower.propFgName, MusicPower.fromJsValue),
      name = find(Name.propFgName, Name.fromJsValue),
      power = find(Power.propFgName, Power.fromJsValue),
      rgb = find(Rgb.propFgName, Rgb.fromJsValue),
      saturation = find(Saturation.propFgName, Saturation.fromJsValue),
      temperature = find(Temperature.propFgName, Temperature.fromJsValue),
      timerValue = find(TimerValue.propFgName, TimerValue.fromJsValue),
      //
      bgBrightness = find(Brightness.propBgName, Brightness.fromJsValue),
      bgColorMode = find(ColorMode.propBgName, ColorMode.fromJsValue),
      bgFlowExpression = find(FlowExpression.propBgName, FlowExpression.fromJsValue),
      bgFlowPower = find(FlowPower.propBgName, FlowPower.fromJsValue),
      bgHue = find(Hue.propBgName, Hue.fromJsValue),
      bgPower = find(Power.propBgName, Power.fromJsValue),
      bgRgb = find(Rgb.propBgName, Rgb.fromJsValue),
      bgSaturation = find(Saturation.propBgName, Saturation.fromJsValue),
      bgTemperature = find(Temperature.propBgName, Temperature.fromJsValue),
      //
      nlBrightness = find(Brightness.propNlName, Brightness.fromJsValue),
      activeMode = find(ActiveMode.propFgName, ActiveMode.fromJsValue)
    )
  }

  def empty = new DeviceInfoChange()

  private implicit def optionToChange[A](opt: Option[A]): Change[A] = Change.fromOption(opt)

}
