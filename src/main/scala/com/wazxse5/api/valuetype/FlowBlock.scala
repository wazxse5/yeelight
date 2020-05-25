package com.wazxse5.api.valuetype

case class FlowBlock(duration: Duration, mode: FlowBlockMode, value: Int, brightness: Brightness) {

  def toJsonValue: String = s"${duration.value},${mode.value},$value,${brightness.value}"

  def isValid: Boolean = {
    duration.isValid && mode.isValid && brightness.isValid && isValueValid
  }

  private def isValueValid: Boolean = mode match {
    case RgbFlowBlockMode => Rgb(value).isValid
    case TemperatureFlowBlockMode => Temperature(value).isValid
    case SleepFlowBlockMode => true
  }
}

object FlowBlock {
  def apply(duration: Int, mode: FlowBlockMode, value: Int, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), mode, value, Brightness(brightness))
  }

  def apply(duration: Int, value: Rgb, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), FlowBlockMode.rgb, value.value, Brightness(brightness))
  }

  def apply(duration: Int, value: Temperature, brightness: Int): FlowBlock = {
    new FlowBlock(Duration(duration), FlowBlockMode.temperature, value.value, Brightness(brightness))
  }
}
