package com.wazxse5.valuetype

import com.wazxse5.snapshot.SnapshotInfo
import com.wazxse5.valuetype.FlowExpression.snapshotName
import play.api.libs.json.{JsArray, JsString, JsValue}

case class FlowExpression(value: Option[Seq[FlowBlock]], isBackground: Boolean) extends PropAndParam[Seq[FlowBlock]] {

  override def companion: PropAndParamCompanion = FlowExpression

  override def strValue: String = value match {
    case Some(v) => v.map(_.toJsonParam).mkString(",")
    case None => ValueType.unknown
  }

  override def paramValue: JsValue = JsString(strValue)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName,
    value match {
      case Some(v) => JsArray(v.map(_.snapshotInfo.value))
      case None => JsString(ValueType.unknown)
    }
  )

  override def isValid: Boolean = value.nonEmpty && value.get.forall(_.isValid)
}

object FlowExpression extends PropAndParamCompanion {
  val snapshotName: String = "flowExpression"
  val paramName: String = "flow_expression"
  val propFgName: String = "flow_params"
  override val propBgName: String = "bg_flow_params"

  def unknown: FlowExpression = new FlowExpression(None, isBackground = false)
  def unknown(isBackground: Boolean): FlowExpression = new FlowExpression(None, isBackground)

  def apply(value: String, isBackground: Boolean = false): FlowExpression = {
    val flowBlocksGroups = value.split(",").grouped(4).toSeq
    val flowBlocks = flowBlocksGroups.map(f => FlowBlock(f(0), f(1), f(2), f(3)))
    new FlowExpression(Some(flowBlocks), isBackground)
  }
}


