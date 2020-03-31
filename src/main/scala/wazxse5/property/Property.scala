package wazxse5.property

import java.time.LocalDateTime

trait Property[A] {
  def name: String

  def bgName: String

  def value: A

  def isBackground: Boolean

  def lastRefresh: Option[LocalDateTime] = None

}
