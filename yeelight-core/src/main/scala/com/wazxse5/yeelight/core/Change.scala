package com.wazxse5.yeelight.core

sealed abstract class Change[+V] {
  def isModify: Boolean = false
  def isKeep: Boolean = false
  def isRemove: Boolean = false
  def toOption: Option[V]
}

final case class Modify[+V](v: V) extends Change[V] {
  override def isModify: Boolean = true
  override def toOption: Option[V] = Some(v)
}

case object Keep extends Change[Nothing] {
  override def isKeep: Boolean = true
  override def toOption: Option[Nothing] = None
}

case object Remove extends Change[Nothing] {
  override def isKeep: Boolean = true
  override def toOption: Option[Nothing] = None
}

object Change {

  def fromOption[V](opt: Option[V]): Change[V] = opt match {
    case Some(value) => Modify(value)
    case None => Keep
  }

  implicit class OptionChange[V](private val value: Option[V]) {
    def withChange(c: Change[V]): Option[V] = c match {
      case Keep => value
      case Remove => None
      case Modify(newValue) => Some(newValue)
    }
  }
}
