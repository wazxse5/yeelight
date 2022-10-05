package com.wazxse5.yeelight.api

import com.wazxse5.yeelight.api.valuetype._

import java.lang.{String => JString}
import java.util.{List => JList}
import scala.jdk.CollectionConverters._

trait YeelightState {

  def isConnected: Boolean

  def address: String

  def port: Int

  def firmwareVersion: String

  def supportedCommands: Seq[String]

  def supportedCommandsJava: JList[JString] = supportedCommands.asJava

  def power: Power

  def brightness: Brightness

  def temperature: Temperature

  def rgb: Rgb

  def hue: Hue

  def saturation: Saturation

}
