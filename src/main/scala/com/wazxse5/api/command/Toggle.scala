package com.wazxse5.api.command
import com.wazxse5.api.valuetype.Parameter

case object Toggle extends YeelightCommand {
  override def name: String = "toggle"

  override def minParameters: Int = 0

  override def maxParameters: Int = 0

  override def params: Seq[Parameter[_]] = Seq.empty
}
