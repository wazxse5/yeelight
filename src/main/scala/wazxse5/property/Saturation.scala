package wazxse5.property

case class Saturation(value: Int, isBackground: Boolean = false) extends Property[Int] {
  override def name: String = "sat"

  override def bgName: String = "bg_sat"
}

object Saturation {

  object Unknown extends Saturation(0)

}