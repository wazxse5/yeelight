package wazxse5

import scala.util.Random

class UID private(value: String) {
  def uid: String = value
}

object UID {
  def generate(length: Int): UID = {
    val generated = Random.alphanumeric.take(length)
    new UID(generated.mkString)
  }

  def generate(): UID = generate(8)
}
