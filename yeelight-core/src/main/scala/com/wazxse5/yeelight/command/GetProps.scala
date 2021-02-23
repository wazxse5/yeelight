package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{PropValueType, PropertyName}

import scala.util.{Failure, Success, Try}

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

    def get(i: Int): OptionalParameter[PropertyName] = Try(propNames(i)) match {
      case Success(propertyName) => OptionalParameter(Some(propertyName))
      case Failure(_) => Parameter.empty
    }

    new GetProps(
      propNames.head,
      get(1), get(2), get(3), get(4), get(5), get(6), get(7),
      get(8), get(9), get(10), get(11), get(12), get(13), get(14),
      get(15), get(16), get(17), get(18), get(19), get(20), get(21), get(22)
    )
  }
}
