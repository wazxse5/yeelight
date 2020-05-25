package com.wazxse5.api

import scala.util.Random

case class InternalId private(id: String)

object InternalId {

  def generate(length: Int): InternalId = {
    val generated = Random.alphanumeric.take(length)
    new InternalId(generated.mkString)
  }

  def generate: InternalId = generate(8)
}
