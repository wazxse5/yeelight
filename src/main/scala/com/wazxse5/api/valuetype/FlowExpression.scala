package com.wazxse5.api.valuetype

case class FlowExpression(value: Seq[FlowBlock], isBackground: Boolean) extends Property[Seq[FlowBlock]] with Parameter[Seq[FlowBlock]] {

  override val propFgName: String = FlowExpression.propFgName

  override val propBgName: Option[String] = Some(FlowExpression.propBgName)

  override val paramName: String = FlowExpression.paramName

  override def toJson: JsonValueType[_] = {
    val string = value.map(_.toJsonValue).mkString(",")
    JsonStringValueType(string)
  }

  override def isValid: Boolean = {
    value.nonEmpty && value.forall(_.isValid)
  }
}

object FlowExpression {
  val propFgName: String = "flow_params"
  val propBgName: String = "bg_flow_params"
  val paramName: String = "flow_expression"

  def apply(value: String, isBackground: Boolean = false): FlowExpression = new FlowExpression(Seq.empty, isBackground) // TODO
}


