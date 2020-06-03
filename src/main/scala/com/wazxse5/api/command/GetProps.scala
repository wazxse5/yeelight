package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Parameter, Property, PropertyName}

case class GetProps(p: PropertyName*) extends YeelightCommand {
  override val name: String = GetProps.name

  override val minParameters: Int = 1

  override val maxParameters: Int = 23

  override def params: Seq[Parameter[_]] = p.toList
}

object GetProps {
  val name: String = "get_prop"

  def all: GetProps = {
    val propNames = Property.names.map(PropertyName(_))
    GetProps(propNames:_*)
  }
}
