package com.wazxse5.valuetype

import com.wazxse5.snapshot.SnapshotInfo
import com.wazxse5.valuetype.FlowExpression.snapshotName
import play.api.libs.json.{JsArray, JsString, JsValue}

case class FlowExpression(value: Seq[FlowBlock], isBackground: Boolean) extends PropAndParam[Seq[FlowBlock]] {

  override def companion: PropAndParamCompanion = FlowExpression

  override def strValue: String = value.map(_.toJsonParam).mkString(",")

  override def paramValue: JsValue = JsString(strValue)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName, JsArray(value.map(_.snapshotInfo.value)))

  override def isValid: Boolean = {
    value.nonEmpty && value.forall(_.isValid)
  }
}

object FlowExpression extends PropAndParamCompanion {
  val snapshotName: String = "flowExpression"
  val paramName: String = "flow_expression"
  val propFgName: String = "flow_params"
  override val propBgName: String = "bg_flow_params"

  def apply(value: String, isBackground: Boolean = false): FlowExpression = new FlowExpression(Seq.empty, isBackground) // TODO
}


