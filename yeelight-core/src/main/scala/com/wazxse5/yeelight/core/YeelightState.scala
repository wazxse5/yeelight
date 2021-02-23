package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.YeelightState.snapshotName
import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.yeelight.valuetype._
import org.joda.time.DateTime
import play.api.libs.json.Json

trait YeelightState extends Snapshotable {
  def brightness: Property[Brightness]
  def colorMode: Property[ColorMode]
  def flowExpression: Property[FlowExpression]
  def flowPower: Property[FlowPower]
  def hue: Property[Hue]
  def musicPower: Property[MusicPower]
  def name: Property[Name]
  def power: Property[Power]
  def rgb: Property[Rgb]
  def saturation: Property[Saturation]
  def temperature: Property[Temperature]
  def timerValue: Property[TimerValue]

  def bgBrightness: Property[Brightness]
  def bgColorMode: Property[ColorMode]
  def bgFlowExpression: Property[FlowExpression]
  def bgFlowPower: Property[FlowPower]
  def bgHue: Property[Hue]
  def bgPower: Property[Power]
  def bgRgb: Property[Rgb]
  def bgSaturation: Property[Saturation]
  def bgTemperature: Property[Temperature]

  def nlBrightness: Property[Brightness]
  def activeMode: Property[ActiveMode]

  def lastUpdate: DateTime

  override final def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, Json.obj(
      Brightness.name -> brightness.strValueOrUnknown,
      ColorMode.name -> colorMode.strValueOrUnknown,
      FlowExpression.name -> flowExpression.strValueOrUnknown,
      FlowPower.name -> flowPower.strValueOrUnknown,
      Hue.name -> hue.strValueOrUnknown,
      MusicPower.name -> musicPower.strValueOrUnknown,
      Name.name -> name.strValueOrUnknown,
      Power.name -> power.strValueOrUnknown,
      Rgb.name -> rgb.strValueOrUnknown,
      Saturation.name -> saturation.strValueOrUnknown,
      Temperature.name -> temperature.strValueOrUnknown,
      TimerValue.name -> timerValue.strValueOrUnknown
    )
  )
}

object YeelightState {
  val snapshotName = "yeelightState"
}