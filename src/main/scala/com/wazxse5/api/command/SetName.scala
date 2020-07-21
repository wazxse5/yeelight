package com.wazxse5.api.command

import com.wazxse5.api.valuetype.Name

case class SetName(p1: Name) extends YeelightCommand1 {
  override val name: String = "set_name"

  override def p1Mandatory: Boolean = true

}

object SetName {
  def apply(name: String): SetName = new SetName(Name(name))
}
