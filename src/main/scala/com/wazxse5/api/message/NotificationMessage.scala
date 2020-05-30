package com.wazxse5.api.message

import com.wazxse5.api.InternalId
import com.wazxse5.api.valuetype._
import play.api.libs.json.{JsResultException, JsValue, Json}

case class NotificationMessage private (newProps: Seq[Property[_]], deviceInternalId: InternalId, json: JsValue, isValid: Boolean = true) extends ApiConnectedMessage {

  def propByName(propName: String): Option[Property[_]] = newProps.find(_.propName == propName)

  def brightness: Option[Brightness] = propByName(Brightness.propFgName).map(_.asInstanceOf[Brightness])

  def colorMode: Option[ColorMode] = propByName(ColorMode.propFgName).map(_.asInstanceOf[ColorMode])

  def hue: Option[Hue] = propByName(Hue.propFgName).map(_.asInstanceOf[Hue])

  def power: Option[Power] = propByName(Power.propFgName).map(_.asInstanceOf[Power])

  def rgb: Option[Rgb] = propByName(Rgb.propFgName).map(_.asInstanceOf[Rgb])

  def saturation: Option[Saturation] = propByName(Saturation.propFgName).map(_.asInstanceOf[Saturation])

  def temperature: Option[Temperature] = propByName(Temperature.propFgName).map(_.asInstanceOf[Temperature])

  override def text: String = Json.stringify(json)

}

object NotificationMessage {

  def apply(json: JsValue, deviceInternalId: InternalId): NotificationMessage = {
    try {
      val method = (json \ "method").as[String]
      val params = (json \ "params").as[Map[String, JsValue]]
      val newProps = params.map(p => Property.applyFromJsValue(p._1, p._2))
      new NotificationMessage(newProps.toSeq, deviceInternalId, json)
    } catch {
      case e: JsResultException =>
        println(Json.prettyPrint(json))
        new NotificationMessage(Seq.empty, deviceInternalId, json, false)
    }

  }

}
