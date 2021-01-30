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
  // TODO: nl_br, active_mode

  def lastUpdate: DateTime

  override final def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, Json.obj(
      Brightness.snapshotName -> brightness.strValueOrUnknown,
      ColorMode.snapshotName -> colorMode.strValueOrUnknown,
      FlowExpression.snapshotName -> flowExpression.strValueOrUnknown,
      FlowPower.snapshotName -> flowPower.strValueOrUnknown,
      Hue.snapshotName -> hue.strValueOrUnknown,
      MusicPower.snapshotName -> musicPower.strValueOrUnknown,
      Name.snapshotName -> name.strValueOrUnknown,
      Power.snapshotName -> power.strValueOrUnknown,
      Rgb.snapshotName -> rgb.strValueOrUnknown,
      Saturation.snapshotName -> saturation.strValueOrUnknown,
      Temperature.snapshotName -> temperature.strValueOrUnknown,
      TimerValue.snapshotName -> timerValue.strValueOrUnknown,
      // TODO: co z background
    )
  )
}

object YeelightState {
  val snapshotName = "yeelightState"
}