package com.wazxse5.api.command
import com.wazxse5.api.valuetype.{Parameter, SceneClass, SceneValue}

case class SetScene(p1: SceneClass, p2: SceneValue[_], p3: SceneValue[_], p4: SceneValue[_]) extends YeelightCommand {
  override val name: String = "set_scene"

  override val minParameters: Int = 3

  override val maxParameters: Int = 4

  override def params: Seq[Parameter[_]] = List(p1, p2, p3, p4) // TODO: obsługa gdy czwarty parametr nie jest podany
}

object SetScene {

}
