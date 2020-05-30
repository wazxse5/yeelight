package com.wazxse5.api.command

import com.wazxse5.api.valuetype.Parameter
import play.api.libs.json.JsValue


trait YeelightCommand {
  def name: String

  def minParameters: Int

  def maxParameters: Int

  def params: Seq[Parameter[_]]

  def args: Seq[JsValue] = params.map(_.toJson)

  def isValid: Boolean = true // TODO: walidacja komend
}


// TODO: Dodać operacje dla background light