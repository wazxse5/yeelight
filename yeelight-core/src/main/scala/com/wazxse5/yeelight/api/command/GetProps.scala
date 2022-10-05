package com.wazxse5.yeelight.api.command
import com.wazxse5.yeelight.api.valuetype.PropertyName
import play.api.libs.json.JsValue

case class GetProps(propertyNames: Seq[PropertyName]) extends YeelightCommand {
  require(propertyNames.nonEmpty)

  override def name: String = GetProps.commandName

  override def args: Seq[JsValue] = propertyNames.map(_.paramValue)
}

object GetProps {
  val commandName: String = "get_prop"

  def all: GetProps = GetProps(PropertyName.all)
}
