package com.wazxse5.yeelight.core

sealed trait Change[+V]

case class Modify[V](newValue: V) extends Change[V]

case object Keep extends Change[Nothing]

case object Remove extends Change[Nothing]

object Change {
  implicit class OptionChange[V](private val value: Option[V]) {
    def withChange(c: Change[V]): Option[V] = c match {
      case Keep => value
      case Remove => None
      case Modify(newValue) => Some(newValue)
    }

    def toChangeModifyOrKeep: Change[V] = value match {
      case Some(value) => Modify(value)
      case None => Keep
    }
  }
}
