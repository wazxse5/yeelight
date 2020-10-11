package com.wazxse5.core

import com.wazxse5.command.GetProps
import com.wazxse5.message.{CommandMessage, CommandResultMessage, NotificationMessage}
import com.wazxse5.valuetype._
import play.api.libs.json.JsString

case class StateUpdate(newProps: Seq[Property[_]]) {
  def propByName(propName: String): Option[Property[_]] = newProps.find(_.propName == propName)
  //
  def brightness: Option[Brightness] = propByName(Brightness.propFgName).map(_.asInstanceOf[Brightness])
  def colorMode: Option[ColorMode] = propByName(ColorMode.propFgName).map(_.asInstanceOf[ColorMode])
  def flowExpression: Option[FlowExpression] = propByName(FlowExpression.propFgName).map(_.asInstanceOf[FlowExpression])
  def flowPower: Option[FlowPower] = propByName(FlowPower.propFgName).map(_.asInstanceOf[FlowPower])
  def hue: Option[Hue] = propByName(Hue.propFgName).map(_.asInstanceOf[Hue])
  def musicPower: Option[MusicPower] = propByName(MusicPower.propFgName).map(_.asInstanceOf[MusicPower])
  def name: Option[Name] = propByName(Name.propFgName).map(_.asInstanceOf[Name])
  def power: Option[Power] = propByName(Power.propFgName).map(_.asInstanceOf[Power])
  def rgb: Option[Rgb] = propByName(Rgb.propFgName).map(_.asInstanceOf[Rgb])
  def saturation: Option[Saturation] = propByName(Saturation.propFgName).map(_.asInstanceOf[Saturation])
  def temperature: Option[Temperature] = propByName(Temperature.propFgName).map(_.asInstanceOf[Temperature])
  def timerValue: Option[TimerValue] = propByName(TimerValue.propFgName).map(_.asInstanceOf[TimerValue])
  //
  def bgBrightness: Option[Brightness] = propByName(Brightness.propBgName).map(_.asInstanceOf[Brightness])
  def bgColorMode: Option[ColorMode] = propByName(ColorMode.propBgName).map(_.asInstanceOf[ColorMode])
  def bgFlowExpression: Option[FlowExpression] = propByName(FlowExpression.propBgName).map(_.asInstanceOf[FlowExpression])
  def bgFlowPower: Option[FlowPower] = propByName(FlowPower.propBgName).map(_.asInstanceOf[FlowPower])
  def bgHue: Option[Hue] = propByName(Hue.propBgName).map(_.asInstanceOf[Hue])
  def bgPower: Option[Power] = propByName(Power.propBgName).map(_.asInstanceOf[Power])
  def bgRgb: Option[Rgb] = propByName(Rgb.propBgName).map(_.asInstanceOf[Rgb])
  def bgSaturation: Option[Saturation] = propByName(Saturation.propBgName).map(_.asInstanceOf[Saturation])
  def bgTemperature: Option[Temperature] = propByName(Temperature.propBgName).map(_.asInstanceOf[Temperature])
}

object StateUpdate {
  def apply(notification: NotificationMessage): StateUpdate = {
    val newProps = notification.params.map(p => Property.applyFromJsValue(p._1, p._2))
    new StateUpdate(newProps.toSeq)
  }

  def apply(message: CommandMessage, result: CommandResultMessage): StateUpdate = {
    if (message.commandName == GetProps.name && message.id == result.id) {
      val propsNames = message.arguments.map(_.asInstanceOf[JsString]).map(_.value)
      val propsResults = result.result.getOrElse(Seq.empty)
      val pairs = propsNames.zip(propsResults).filter(_._2.nonEmpty)
      val newProps = pairs.map(p => Property.applyByName(p._1, p._2))
      new StateUpdate(newProps)
    }
    else empty
  }

  def empty = new StateUpdate(Seq.empty)
}
