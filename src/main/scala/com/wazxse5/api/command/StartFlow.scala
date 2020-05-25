package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{FlowAction, FlowCount, FlowExpression, Parameter}

case class StartFlow(p1: FlowCount, p2: FlowAction, p3: FlowExpression) extends YeelightCommand {
  override def name: String = "start_cf"

  override def minParameters: Int = 3

  override def maxParameters: Int = 3

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}

object StartFlow {
  def apply(expression: FlowExpression): StartFlow = {
    new StartFlow(FlowCount.infinite, FlowAction.stay, expression)
  }
}
