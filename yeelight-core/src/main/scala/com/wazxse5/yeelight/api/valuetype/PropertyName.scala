package com.wazxse5.yeelight.api.valuetype

case class PropertyName private(value: String) extends StringParamValueType

object PropertyName extends StringValueTypeCompanion[PropertyName] {
  val brightness: PropertyName = new PropertyName(Brightness.propFgName)
  val brightnessBg: PropertyName = new PropertyName(Brightness.propBgName)
  val hue: PropertyName = new PropertyName(Hue.propFgName)
  val hueBg: PropertyName = new PropertyName(Hue.propBgName)
  val power: PropertyName = new PropertyName(Power.propFgName)
  val powerBg: PropertyName = new PropertyName(Power.propBgName)
  val rgb: PropertyName = new PropertyName(Rgb.propFgName)
  val rgbBg: PropertyName = new PropertyName(Rgb.propBgName)
  val saturation: PropertyName = new PropertyName(Saturation.propFgName)
  val saturationBg: PropertyName = new PropertyName(Saturation.propBgName)
  val temperature: PropertyName = new PropertyName(Temperature.propFgName)
  val temperatureBg: PropertyName = new PropertyName(Temperature.propBgName)

  val allFg: Seq[PropertyName] = Seq(brightness, hue, power, rgb, saturation, temperature)
  val allBg: Seq[PropertyName] = Seq(brightnessBg, hueBg, powerBg, rgbBg, saturationBg, temperatureBg)
  val all: Seq[PropertyName] = allFg ++ allBg
  val typeByValue: Map[String, PropertyName] = all.map(v => v.value -> v).toMap

  override def isValid(value: String): Boolean = typeByValue.contains(value)

  override protected def create(value: String): PropertyName = typeByValue(value)
}
