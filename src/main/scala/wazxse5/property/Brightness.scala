package wazxse5.property

case class Brightness(value: Int, isBackground: Boolean = false) extends Property[Int] {
  override def name: String = "bright"

  override def bgName: String = "bg_bright"
}

object Brightness {

  object Unknown extends Brightness(0)

}
