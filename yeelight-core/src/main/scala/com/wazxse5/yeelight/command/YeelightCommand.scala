package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.yeelight.valuetype.ParamValueType
import play.api.libs.json.{JsArray, JsValue, Json}


trait YeelightCommand extends Snapshotable {
  def companion: YeelightCommandCompanion
  def name: String = companion.commandName

  type PARAM = Parameter[_ <: ParamValueType[_]]
  def params: Seq[PARAM]
  def args: Seq[JsValue] = params.filterNot(_.isEmpty).map(_.value.paramValue)
  def minParameters: Int = params.count(_.isMandatory)
  def maxParameters: Int = params.size
  def isValid: Boolean = params.forall(_.value.isValid)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    companion.snapshotName,
    Json.obj(
      "minParams" -> minParameters,
      "maxParams" -> maxParameters,
      "isValid" -> isValid,
      "args" -> JsArray(params.map(_.value.paramValue))
    )
  )
}

trait YeelightCommand0 extends YeelightCommand {
  override final val params = Seq.empty
}

trait YeelightCommand1 extends YeelightCommand {
  def p1: PARAM
  override def params = Seq(p1)
}

trait YeelightCommand2 extends YeelightCommand {
  def p1: PARAM
  def p2: PARAM
  override def params = Seq(p1, p2)
}

trait YeelightCommand3 extends YeelightCommand {
  def p1: PARAM
  def p2: PARAM
  def p3: PARAM
  override def params = Seq(p1, p2, p3)
}

trait YeelightCommand4 extends YeelightCommand {
  def p1: PARAM
  def p2: PARAM
  def p3: PARAM
  def p4: PARAM
  override def params = Seq(p1, p2, p3, p4)
}

trait YeelightCommand23 extends YeelightCommand {
  def p1: PARAM
  def p2: PARAM
  def p3: PARAM
  def p4: PARAM
  def p5: PARAM
  def p6: PARAM
  def p7: PARAM
  def p8: PARAM
  def p9: PARAM
  def p10: PARAM
  def p11: PARAM
  def p12: PARAM
  def p13: PARAM
  def p14: PARAM
  def p15: PARAM
  def p16: PARAM
  def p17: PARAM
  def p18: PARAM
  def p19: PARAM
  def p20: PARAM
  def p21: PARAM
  def p22: PARAM
  def p23: PARAM
  override def params = Seq(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23)
}
