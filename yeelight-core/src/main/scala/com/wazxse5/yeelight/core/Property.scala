package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.valuetype.PropValueType
import org.joda.time.DateTime

class Property[A <: PropValueType[_]] private (
  _value: Option[A],
  _lastUpdate: DateTime,
  _lastChange: DateTime,
  _isBackground: Boolean
) {
  def value: Option[A] = _value
  def lastUpdate: DateTime = _lastUpdate
  def lastChange: DateTime = _lastChange
  def isBackground: Boolean = _isBackground

  def strValueOrUnknown: String = value.map(_.strValue).getOrElse("unknown")

  def withValue(newValue: Option[A]): Property[A] =  {
    val newLastChange = if (value == newValue) _lastChange else DateTime.now
    new Property(newValue, DateTime.now, newLastChange, _isBackground)
  }

  def withValue(newValue: A): Property[A] = withValue(Some(newValue))

  def withoutValue: Property[A] = withValue(None)

}

object Property {
  def empty[A <: PropValueType[_]]: Property[A] = apply(None, isBackground = false)

  def emptyBackground[A <: PropValueType[_]]: Property[A] = {
    new Property[A](None, DateTime.now, DateTime.now, _isBackground = true)
  }

  def apply[A <: PropValueType[_]](initialValue: Option[A], isBackground: Boolean): Property[A] = {
    new Property[A](initialValue, DateTime.now, DateTime.now, isBackground)
  }
}
