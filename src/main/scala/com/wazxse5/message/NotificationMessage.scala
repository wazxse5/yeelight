package com.wazxse5.message

import com.wazxse5.InternalId
import com.wazxse5.valuetype._

case class NotificationMessage private (
                                         newProps: Seq[Property[_]],
                                         deviceInternalId: InternalId,
                                         text: String,
                                         isValid: Boolean = true
) extends ApiConnectedMessage {

  def propByName(propName: String): Option[Property[_]] = newProps.find(_.propName == propName)

  def brightness: Option[Brightness] = propByName(Brightness.propName).map(_.asInstanceOf[Brightness])

  def colorMode: Option[ColorMode] = propByName(ColorMode.propName).map(_.asInstanceOf[ColorMode])

  def hue: Option[Hue] = propByName(Hue.propName).map(_.asInstanceOf[Hue])

  def power: Option[Power] = propByName(Power.propName).map(_.asInstanceOf[Power])

  def rgb: Option[Rgb] = propByName(Rgb.propName).map(_.asInstanceOf[Rgb])

  def saturation: Option[Saturation] = propByName(Saturation.propName).map(_.asInstanceOf[Saturation])

  def temperature: Option[Temperature] = propByName(Temperature.propName).map(_.asInstanceOf[Temperature])

}

object NotificationMessage {
  private val startText = """{"method":"props","params":{"""
  private val endText = "}}\r\n"

  def apply(messageText: String, deviceInternalId: InternalId): NotificationMessage = {
    try { // TODO: Może da się to ładniej a nie try-catchem
      val m1 = messageText.replace(startText, "").replace(endText, "")
        .replace("\"", "").replace("\r", "").replace("\n", "")
      val m2 = m1.split(',').toSeq // params
      val m3 = m2.map(_.split(':')).map(p => (p.head, p.tail.head)) //  pairs of params
      val m4 = m3.map(pair => Property.applyByName(pair._1, pair._2))
      new NotificationMessage(m4, deviceInternalId, messageText)
    } catch {
      case _: RuntimeException => new NotificationMessage(Seq.empty, deviceInternalId, "", isValid = false)
    }
  }
}
