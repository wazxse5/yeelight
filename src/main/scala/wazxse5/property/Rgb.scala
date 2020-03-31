package wazxse5.property

// TODO: Przerobić na lepiej
case class Rgb(value: Int, isBackground: Boolean = false) extends Property[Int] {
  override def name: String = "rgb"

  override def bgName: String = "bg_rgb"
}

object Rgb {
  object Unknown extends Rgb(0)
}
