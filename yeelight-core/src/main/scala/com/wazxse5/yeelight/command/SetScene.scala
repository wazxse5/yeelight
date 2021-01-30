package com.wazxse5.yeelight.command
import com.wazxse5.yeelight.valuetype._

trait SetScene extends YeelightCommand4 {
  override def companion: YeelightCommandCompanion = SetScene
  override def p1: MandatoryParameter[SceneClass]
}

object SetScene extends YeelightCommandCompanion {
  override val commandName: String = "set_scene"
  override val snapshotName: String = "setScene"
}

case class SetSceneRgb(
  p2: MandatoryParameter[Rgb],
  p3: MandatoryParameter[Brightness]
) extends SetScene {
  override val p1: MandatoryParameter[SceneClass] = MandatoryParameter(SceneClass.rgb)
  override val p4: PARAM = Parameter.empty
}

case class SetSceneHsv(
  p2: MandatoryParameter[Hue],
  p3: MandatoryParameter[Brightness]
) extends SetScene {
  override val p1: MandatoryParameter[SceneClass] = MandatoryParameter(SceneClass.hsv)
  override val p4: PARAM = Parameter.empty
}

case class SetSceneTemperature(
  p2: MandatoryParameter[Temperature],
  p3: MandatoryParameter[Brightness]
) extends SetScene {
  override val p1: MandatoryParameter[SceneClass] = MandatoryParameter(SceneClass.temperature)
  override val p4: PARAM = Parameter.empty
}

case class SetSceneFlow(
  p2: MandatoryParameter[FlowCount],
  p3: MandatoryParameter[FlowAction],
  p4: MandatoryParameter[FlowExpression]
) extends SetScene {
  override val p1: MandatoryParameter[SceneClass] = MandatoryParameter(SceneClass.flow)
}

case class SetSceneAutoDelayOff(
  p2: MandatoryParameter[Brightness],
  p3: MandatoryParameter[TimerValue]
) extends SetScene {
  override val p1: MandatoryParameter[SceneClass] = MandatoryParameter(SceneClass.delayOff)
  override val p4: PARAM = Parameter.empty
}