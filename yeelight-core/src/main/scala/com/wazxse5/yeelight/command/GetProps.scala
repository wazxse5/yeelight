package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{PropValueType, PropertyName}

case class GetProps(
  p1: MandatoryParameter[PropertyName],
  p2: OptionalParameter[PropertyName],
  p3: OptionalParameter[PropertyName],
  p4: OptionalParameter[PropertyName],
  p5: OptionalParameter[PropertyName],
  p6: OptionalParameter[PropertyName],
  p7: OptionalParameter[PropertyName],
  p8: OptionalParameter[PropertyName],
  p9: OptionalParameter[PropertyName],
  p10: OptionalParameter[PropertyName],
  p11: OptionalParameter[PropertyName],
  p12: OptionalParameter[PropertyName],
  p13: OptionalParameter[PropertyName],
  p14: OptionalParameter[PropertyName],
  p15: OptionalParameter[PropertyName],
  p16: OptionalParameter[PropertyName],
  p17: OptionalParameter[PropertyName],
  p18: OptionalParameter[PropertyName],
  p19: OptionalParameter[PropertyName],
  p20: OptionalParameter[PropertyName],
  p21: OptionalParameter[PropertyName],
  p22: OptionalParameter[PropertyName],
  p23: OptionalParameter[PropertyName]
) extends YeelightCommand23 {
  override def companion: YeelightCommandCompanion = GetProps
}

object GetProps extends YeelightCommandCompanion {
  override val commandName: String = "get_prop"
  override val snapshotName: String = "getProps"

  def all: GetProps = {
    val propNames = PropValueType.names.map(PropertyName(_))
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
