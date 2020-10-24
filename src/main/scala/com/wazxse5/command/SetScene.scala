package com.wazxse5.command
import com.wazxse5.valuetype.{Parameter, SceneClass, SceneValue}

case class SetScene(p1: SceneClass, p2: SceneValue[_], p3: SceneValue[_], p4Opt: Option[SceneValue[_]]) extends YeelightCommand4 {
  override def companion: YeelightCommandCompanion = SetScene

  override def p4: Parameter[_] = p4Opt
  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
  override def p3Mandatory: Boolean = true
  override def p4Mandatory: Boolean = false
}

object SetScene extends YeelightCommandCompanion {
  override val commandName: String = "set_scene"
  override val snapshotName: String = "setScene"
}
