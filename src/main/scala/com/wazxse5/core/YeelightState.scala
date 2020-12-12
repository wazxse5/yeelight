package com.wazxse5.core

import com.wazxse5.core.YeelightState.snapshotName
import com.wazxse5.exception.UnsupportedPropertyException
import com.wazxse5.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.valuetype._
import org.joda.time.DateTime
import play.api.libs.json.Json

trait YeelightState extends Snapshotable {
  def brightness: Brightness = throw new UnsupportedPropertyException
  def colorMode: ColorMode = throw new UnsupportedPropertyException
  def flowExpression: FlowExpression = throw new UnsupportedPropertyException
  def flowPower: FlowPower = throw new UnsupportedPropertyException
  def hue: Hue = throw new UnsupportedPropertyException
  def musicPower: MusicPower = throw new UnsupportedPropertyException
  def name: Name = throw new UnsupportedPropertyException
  def power: Power = throw new UnsupportedPropertyException
  def rgb: Rgb = throw new UnsupportedPropertyException
  def saturation: Saturation = throw new  UnsupportedPropertyException
  def temperature: Temperature = throw new UnsupportedPropertyException
  def timerValue: TimerValue = throw new UnsupportedPropertyException

  def bgBrightness: Brightness = throw new UnsupportedPropertyException
  def bgColorMode: ColorMode = throw new UnsupportedPropertyException
  def bgFlowExpression: FlowExpression = throw new UnsupportedPropertyException
  def bgFlowPower: FlowPower = throw new UnsupportedPropertyException
  def bgHue: Hue = throw new UnsupportedPropertyException
  def bgPower: Power = throw new UnsupportedPropertyException
  def bgRgb: Rgb = throw new UnsupportedPropertyException
  def bgSaturation: Saturation = throw new UnsupportedPropertyException
  def bgTemperature: Temperature = throw new UnsupportedPropertyException
  // TODO: nl_br, active_mode

  def lastUpdate: DateTime = throw new UnsupportedPropertyException

  override final def snapshotInfo: SnapshotInfo = SnapshotInfo(
    snapshotName, Json.obj(
      Brightness.snapshotName -> brightness.snapshotInfo.value,
      ColorMode.snapshotName -> colorMode.snapshotInfo.value,
      FlowExpression.snapshotName -> flowExpression.snapshotInfo.value,
      FlowPower.snapshotName -> flowPower.snapshotInfo.value,
      Hue.snapshotName -> hue.snapshotInfo.value,
      MusicPower.snapshotName -> musicPower.snapshotInfo.value,
      Name.snapshotName -> name.snapshotInfo.value,
      Power.snapshotName -> power.snapshotInfo.value,
      Rgb.snapshotName -> rgb.snapshotInfo.value,
      Saturation.snapshotName -> saturation.snapshotInfo.value,
      Temperature.snapshotName -> temperature.snapshotInfo.value,
      TimerValue.snapshotName -> timerValue.snapshotInfo.value,
      // TODO: co z background
    )
  )
}

object YeelightState {
  val snapshotName = "yeelightState"
}