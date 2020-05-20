package com.wazxse5.command

import com.wazxse5.valuetype.{JsonValueType, Parameter}


trait YeelightCommand {
  def name: String

  def minParameters: Int

  def maxParameters: Int

  def params: Seq[Parameter[_]]

  def args: Seq[JsonValueType[_]] = params.map(_.toJson)

  def isValid: Boolean = true // TODO: walidacja komend
}


// TODO: Dodać operacje dla background light