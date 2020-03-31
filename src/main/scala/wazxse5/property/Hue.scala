package wazxse5.property

case class Hue(value: Int, isBackground: Boolean = false) extends Property[Int] {
  override def name: String = "saturation"

  override def bgName: String = "bg_hue"
}

object Hue {

  object Unknown extends Hue(0)

}