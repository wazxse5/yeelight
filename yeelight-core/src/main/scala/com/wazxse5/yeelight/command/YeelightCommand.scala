package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import com.wazxse5.yeelight.valuetype.Parameter
import play.api.libs.json.{JsArray, JsValue, Json}


trait YeelightCommand extends Snapshotable {
  def companion: YeelightCommandCompanion
  def name: String = companion.commandName

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    companion.snapshotName,
    Json.obj(
      "minParams" -> minParameters,
      "maxParams" -> maxParameters,
      "isValid" -> isValid,
      "params" -> JsArray(params.map(_.snapshotInfo.value))
    )
  )

  def minParameters: Int
  def maxParameters: Int
  def params: Seq[Parameter[_]]
  def args: Seq[JsValue] = params.map(_.paramValue)
  def isValid: Boolean = params.forall(_.isValid)
}

trait YeelightCommand0 extends YeelightCommand {
  override final def minParameters: Int = 0
  override final def maxParameters: Int = 0
  override def params: Seq[Parameter[_]] = Seq.empty
}

trait YeelightCommand1 extends YeelightCommand {
  def p1: Parameter[_]
  def p1Mandatory: Boolean
  override final def minParameters: Int = Seq(p1Mandatory).count(_ == true)
  override final def maxParameters: Int = 1
  def params: Seq[Parameter[_]] = Seq(p1).filterNot(_.isEmptyParam)
}

trait YeelightCommand2 extends YeelightCommand {
  def p1: Parameter[_]
  def p2: Parameter[_]
  def p1Mandatory: Boolean
  def p2Mandatory: Boolean
  override final def minParameters: Int = Seq(p1Mandatory, p2Mandatory).count(_ == true)
  override final def maxParameters: Int = 2
  def params: Seq[Parameter[_]] = Seq(p1, p2).filterNot(_.isEmptyParam)
}

trait YeelightCommand3 extends YeelightCommand {
  def p1: Parameter[_]
  def p2: Parameter[_]
  def p3: Parameter[_]
  def p1Mandatory: Boolean
  def p2Mandatory: Boolean
  def p3Mandatory: Boolean
  override final def minParameters: Int = Seq(p1Mandatory, p2Mandatory, p3Mandatory).count(_ == true)
  override final def maxParameters: Int = 3
  def params: Seq[Parameter[_]] = Seq(p1, p2, p3).filterNot(_.isEmptyParam)
}

trait YeelightCommand4 extends YeelightCommand {
  def p1: Parameter[_]
  def p2: Parameter[_]
  def p3: Parameter[_]
  def p4: Parameter[_]
  def p1Mandatory: Boolean
  def p2Mandatory: Boolean
  def p3Mandatory: Boolean
  def p4Mandatory: Boolean
  override final def minParameters: Int = Seq(p1Mandatory, p2Mandatory, p3Mandatory, p4Mandatory).count(_ == true)
  override final def maxParameters: Int = 4
  def params: Seq[Parameter[_]] = Seq(p1, p2, p3, p4).filterNot(_.isEmptyParam)
}

trait YeelightCommand23 extends YeelightCommand {
  def p1: Parameter[_]
  def p2: Parameter[_]
  def p3: Parameter[_]
  def p4: Parameter[_]
  def p5: Parameter[_]
  def p6: Parameter[_]
  def p7: Parameter[_]
  def p8: Parameter[_]
  def p9: Parameter[_]
  def p10: Parameter[_]
  def p11: Parameter[_]
  def p12: Parameter[_]
  def p13: Parameter[_]
  def p14: Parameter[_]
  def p15: Parameter[_]
  def p16: Parameter[_]
  def p17: Parameter[_]
  def p18: Parameter[_]
  def p19: Parameter[_]
  def p20: Parameter[_]
  def p21: Parameter[_]
  def p22: Parameter[_]
  def p23: Parameter[_]
  def p1Mandatory : Boolean = false
  def p2Mandatory : Boolean = false
  def p3Mandatory : Boolean = false
  def p4Mandatory : Boolean = false
  def p5Mandatory : Boolean = false
  def p6Mandatory : Boolean = false
  def p7Mandatory : Boolean = false
  def p8Mandatory : Boolean = false
  def p9Mandatory : Boolean = false
  def p10Mandatory: Boolean = false
  def p11Mandatory: Boolean = false
  def p12Mandatory: Boolean = false
  def p13Mandatory: Boolean = false
  def p14Mandatory: Boolean = false
  def p15Mandatory: Boolean = false
  def p16Mandatory: Boolean = false
  def p17Mandatory: Boolean = false
  def p18Mandatory: Boolean = false
  def p19Mandatory: Boolean = false
  def p20Mandatory: Boolean = false
  def p21Mandatory: Boolean = false
  def p22Mandatory: Boolean = false
  def p23Mandatory: Boolean = false

  override final def minParameters: Int = Seq(
    p1Mandatory, p2Mandatory, p3Mandatory, p4Mandatory, p5Mandatory, p6Mandatory, p7Mandatory, p8Mandatory,
    p9Mandatory, p10Mandatory, p11Mandatory, p12Mandatory, p13Mandatory, p14Mandatory, p15Mandatory, p16Mandatory,
    p17Mandatory, p18Mandatory, p19Mandatory, p20Mandatory, p21Mandatory, p22Mandatory, p23Mandatory
  ).count(_ == true)

  override final def maxParameters: Int = 23

  def params: Seq[Parameter[_]] = Seq(
    p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20, p21, p22, p23
  ).filterNot(_.isEmptyParam)
}
