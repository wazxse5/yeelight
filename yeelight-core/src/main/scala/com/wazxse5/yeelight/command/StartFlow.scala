package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{FlowAction, FlowCount, FlowExpression}

case class StartFlow(
  p1: MandatoryParameter[FlowCount],
  p2: MandatoryParameter[FlowAction],
  p3: MandatoryParameter[FlowExpression]
) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = StartFlow
}

object StartFlow extends YeelightCommandCompanion {
  override val commandName: String = "start_cf"
  override val snapshotName: String = "startFlow"

  def apply(expression: FlowExpression): StartFlow = {
    new StartFlow(FlowCount.infinite, FlowAction.stay, expression)
  }
}
