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

  def withValue(newValue: A): Property[A] = {
    val newLastChange = if (value.contains(newValue)) _lastChange else DateTime.now
    new Property(Some(newValue), DateTime.now, newLastChange, _isBackground)
  }

  def withoutValue: Property[A] = {
    val newLastChange = if (value.isEmpty) _lastChange else DateTime.now
    new Property(None, DateTime.now, newLastChange, isBackground)
  }

  def withValue(newValue: Option[A]): Property[A] = newValue match {
    case Some(value) => withValue(value)
    case None => withoutValue
  }

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
