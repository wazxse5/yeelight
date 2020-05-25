package com.wazxse5.api.valuetype

sealed trait FlowBlockMode {
  def value: Int

  def isValid: Boolean = {
    value == 1 || value == 2 || value == 7
  }
}

object FlowBlockMode {
  def rgb: FlowBlockMode = RgbFlowBlockMode

  def temperature: FlowBlockMode = TemperatureFlowBlockMode

  def sleep: FlowBlockMode = SleepFlowBlockMode
}

case object RgbFlowBlockMode extends FlowBlockMode {
  override val value: Int = 1
}

case object TemperatureFlowBlockMode extends FlowBlockMode {
  override val value: Int = 2
}

case object SleepFlowBlockMode extends FlowBlockMode {
  override val value: Int = 7
}
