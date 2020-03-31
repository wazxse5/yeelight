package wazxse5.command

import wazxse5.parameter.{JsonValueType, Parameter}


trait YeelightCommand {
  def name: String

  def minParameters: Int

  def maxParameters: Int

  def params: Seq[Parameter[_]]

  def args: Seq[JsonValueType] = params.map(_.toJson)

  def isValid: Boolean = true // TODO: walidacja komend
}


// TODO: Dodać operacje dla background light