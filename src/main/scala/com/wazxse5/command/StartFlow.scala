package com.wazxse5.command

import com.wazxse5.valuetype.{FlowAction, FlowCount, FlowExpression}

case class StartFlow(p1: FlowCount, p2: FlowAction, p3: FlowExpression) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = StartFlow

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
  override def p3Mandatory: Boolean = true
}

object StartFlow extends YeelightCommandCompanion {
  override val commandName: String = "start_cf"
  override val snapshotName: String = "startFlow"

  def apply(expression: FlowExpression): StartFlow = {
    new StartFlow(FlowCount.infinite, FlowAction.stay, expression)
  }
}
