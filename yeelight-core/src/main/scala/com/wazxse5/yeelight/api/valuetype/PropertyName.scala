package com.wazxse5.yeelight.api.valuetype
import play.api.libs.json.{JsString, JsValue}

sealed trait PropertyName extends ParamValueType[String] {
  override def paramValue: JsValue = JsString(value)
}

object PropertyName {
  def brightness: PropertyName = PropertyNameBrightness
  def brightnessBg: PropertyName = PropertyNameBrightnessBg
  def hue: PropertyName = PropertyNameHue
  def hueBg: PropertyName = PropertyNameHueBg
  def power: PropertyName = PropertyNamePower
  def powerBg: PropertyName = PropertyNamePowerBg
  def rgb: PropertyName = PropertyNameRgb
  def rgbBg: PropertyName = PropertyNameRgbBg
  def saturation: PropertyName = PropertyNameSaturation
  def saturationBg: PropertyName = PropertyNameSaturationBg
  def temperature: PropertyName = PropertyNameTemperature
  def temperatureBg: PropertyName = PropertyNameTemperatureBg

  def allFg: Seq[PropertyName] = Seq(brightness, hue, power, rgb, saturation, temperature)
  def allBg: Seq[PropertyName] = Seq(brightnessBg, hueBg, powerBg, rgbBg, saturationBg, temperatureBg)
  val all: Seq[PropertyName] = allFg ++ allBg

  val typeByValue: Map[String, PropertyName] = all.map(v => v.value -> v).toMap

  def fromString(str: String): Option[PropertyName] = typeByValue.get(str)
}

case object PropertyNameBrightness extends PropertyName {
  override def value: String = Brightness.propFgName
}

case object PropertyNameBrightnessBg extends PropertyName {
  override def value: String = Brightness.propBgName
}

case object PropertyNameHue extends PropertyName {
  override def value: String = Hue.propFgName
}

case object PropertyNameHueBg extends PropertyName {
  override def value: String = Hue.propBgName
}

case object PropertyNamePower extends PropertyName {
  override def value: String = Power.propFgName
}

case object PropertyNamePowerBg extends PropertyName {
  override def value: String = Power.propBgName
}

case object PropertyNameRgb extends PropertyName {
  override def value: String = Rgb.propFgName
}

case object PropertyNameRgbBg extends PropertyName {
  override def value: String = Rgb.propBgName
}

case object PropertyNameSaturation extends PropertyName {
  override def value: String = Saturation.propFgName
}

case object PropertyNameSaturationBg extends PropertyName {
  override def value: String = Saturation.propBgName
}

case object PropertyNameTemperature extends PropertyName {
  override def value: String = Temperature.propFgName
}

case object PropertyNameTemperatureBg extends PropertyName {
  override def value: String = Temperature.propBgName
}
