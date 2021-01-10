package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Parameter, Property, PropertyName}

case class GetProps(
  p1: PropertyName,
  p2Opt: PropertyName,
  p3Opt: PropertyName,
  p4Opt: PropertyName,
  p5Opt: PropertyName,
  p6Opt: PropertyName,
  p7Opt: PropertyName,
  p8Opt: PropertyName,
  p9Opt: PropertyName,
  p10Opt: PropertyName,
  p11Opt: PropertyName,
  p12Opt: PropertyName,
  p13Opt: PropertyName,
  p14Opt: PropertyName,
  p15Opt: PropertyName,
  p16Opt: PropertyName,
  p17Opt: PropertyName,
  p18Opt: PropertyName,
  p19Opt: PropertyName,
  p20Opt: PropertyName,
  p21Opt: PropertyName,
  p22Opt: PropertyName,
  p23Opt: PropertyName
) extends YeelightCommand23 {
  override def companion: YeelightCommandCompanion = GetProps

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

object GetProps extends YeelightCommandCompanion {
  override val commandName: String = "get_prop"
  override val snapshotName: String = "getProps"

  def all: GetProps = {
    val propNames = Property.names.map(PropertyName(_))
    new GetProps(
      propNames.head,
      propNames(1),
      propNames(2),
      propNames(3),
      propNames(4),
      propNames(5),
      propNames(6),
      propNames(7),
      propNames(8),
      propNames(9),
      propNames(10),
      propNames(11),
      propNames(12),
      propNames(13),
      propNames(14),
      propNames(15),
      propNames(16),
      propNames(17),
      propNames(18),
      propNames(19),
      propNames(20),
      propNames(21),
      propNames(22)
    )
  }
}
