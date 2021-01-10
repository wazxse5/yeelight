package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.exception.InvalidParamValueException
import com.wazxse5.yeelight.valuetype.ValueType.unknown
import play.api.libs.json.{JsString, JsValue}

sealed trait Effect extends Parameter[String] {
  override def companion: ParamCompanion = Effect

  override def strValue: String = value.getOrElse(unknown)

  override def paramValue: JsValue = JsString(strValue)

  override def isValid: Boolean = value.isDefined && Effect.values.contains(value.get)
}

object Effect extends ParamCompanion {
  val snapshotName: String = "effect"
  val paramName: String = "effect"

  case object Sudden extends Effect {
    override val value: Option[String] = Some("sudden")
  }

  case object Smooth extends Effect {
    override val value: Option[String] = Some("smooth")
  }

  def apply(value: String): Effect = value match {
    case "sudden" => Sudden
    case "smooth" => Smooth
    case _ => throw new InvalidParamValueException(value)
  }

  def sudden: Effect = Sudden
  def smooth: Effect = Smooth

  def values = Set(Sudden.value.get, Smooth.value.get)
}
