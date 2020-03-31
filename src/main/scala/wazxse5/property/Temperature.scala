package wazxse5.property

case class Temperature(value: Int, isBackground: Boolean = false) extends Property[Int] {
  override def name: String = "ct"

  override def bgName: String = "bg_ct"
}

object Temperature {

  object Unknown extends Temperature(0)

}