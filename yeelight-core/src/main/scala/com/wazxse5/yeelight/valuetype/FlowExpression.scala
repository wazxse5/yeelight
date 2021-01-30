package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import com.wazxse5.yeelight.valuetype.FlowExpression.snapshotName
import play.api.libs.json.{JsArray, JsString, JsValue}

import scala.util.Try

case class FlowExpression(value: Seq[FlowBlock]) extends PropAndParamValueType[Seq[FlowBlock]] {
  override def strValue: String = value.map(_.toJsonParam).mkString(",")
  override def paramValue: JsValue = JsString(strValue)
  override def companion: PropAndParamCompanion = FlowExpression
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName, JsArray(value.map(_.snapshotInfo.value)))
  override def isValid: Boolean = value.forall(_.isValid)
}

object FlowExpression extends PropAndParamCompanion {
  override val snapshotName = "flowExpression"
  override val paramName = "flow_expression"
  override val propFgName = "flow_params"
  override val propBgName = "bg_flow_params"

  def fromString(str: String): Option[FlowExpression] = {
    Try {
      val flowBlocksGroups = str.split(",").grouped(4).toSeq
      val flowBlocks = flowBlocksGroups.map(f => FlowBlock(f(0), f(1), f(2), f(3)))
      new FlowExpression(flowBlocks)
    }.filter(_.isValid).toOption
  }
  def fromJsValue(jsValue: JsValue): Option[FlowExpression] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}


