package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Parameter, Property, PropertyName}

case class GetProps(
  p1: PropertyName,
  p2Opt: Option[PropertyName] = None,
  p3Opt: Option[PropertyName] = None,
  p4Opt: Option[PropertyName] = None,
  p5Opt: Option[PropertyName] = None,
  p6Opt: Option[PropertyName] = None,
  p7Opt: Option[PropertyName] = None,
  p8Opt: Option[PropertyName] = None,
  p9Opt: Option[PropertyName] = None,
  p10Opt: Option[PropertyName] = None,
  p11Opt: Option[PropertyName] = None,
  p12Opt: Option[PropertyName] = None,
  p13Opt: Option[PropertyName] = None,
  p14Opt: Option[PropertyName] = None,
  p15Opt: Option[PropertyName] = None,
  p16Opt: Option[PropertyName] = None,
  p17Opt: Option[PropertyName] = None,
  p18Opt: Option[PropertyName] = None,
  p19Opt: Option[PropertyName] = None,
  p20Opt: Option[PropertyName] = None,
  p21Opt: Option[PropertyName] = None,
  p22Opt: Option[PropertyName] = None,
  p23Opt: Option[PropertyName] = None
) extends YeelightCommand23 {
  override val name: String = GetProps.name

  override def p2: Parameter[_] = p2Opt
  override def p3: Parameter[_] = p3Opt
  override def p4: Parameter[_] = p4Opt
  override def p5: Parameter[_] = p5Opt
  override def p6: Parameter[_] = p6Opt
  override def p7: Parameter[_] = p7Opt
  override def p8: Parameter[_] = p8Opt
  override def p9: Parameter[_] = p9Opt
  override def p10: Parameter[_] = p10Opt
  override def p11: Parameter[_] = p11Opt
  override def p12: Parameter[_] = p12Opt
  override def p13: Parameter[_] = p13Opt
  override def p14: Parameter[_] = p14Opt
  override def p15: Parameter[_] = p15Opt
  override def p16: Parameter[_] = p16Opt
  override def p17: Parameter[_] = p17Opt
  override def p18: Parameter[_] = p18Opt
  override def p19: Parameter[_] = p19Opt
  override def p20: Parameter[_] = p20Opt
  override def p21: Parameter[_] = p21Opt
  override def p22: Parameter[_] = p22Opt
  override def p23: Parameter[_] = p23Opt
}

object GetProps {
  val name: String = "get_prop"

  def all: GetProps = {
    val propNames = Property.names.map(PropertyName(_))
    apply(propNames)
  }

  def apply(propNames: Seq[PropertyName]): GetProps = {
    require(propNames.nonEmpty)
    new GetProps(
      propNames.head,
      propNames.lift(1),
      propNames.lift(2),
      propNames.lift(3),
      propNames.lift(4),
      propNames.lift(5),
      propNames.lift(6),
      propNames.lift(7),
      propNames.lift(8),
      propNames.lift(9),
      propNames.lift(10),
      propNames.lift(11),
      propNames.lift(12),
      propNames.lift(13),
      propNames.lift(14),
      propNames.lift(15),
      propNames.lift(16),
      propNames.lift(17),
      propNames.lift(18),
      propNames.lift(19),
      propNames.lift(20),
      propNames.lift(21),
      propNames.lift(22)
    )
  }
}
