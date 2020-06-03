package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Name, Parameter}

case class SetName(p1: Name) extends YeelightCommand {
  override val name: String = "set_name"

  override val minParameters: Int = 1

  override val maxParameters: Int = 1

  override def params: Seq[Parameter[_]] = List(p1)
}
