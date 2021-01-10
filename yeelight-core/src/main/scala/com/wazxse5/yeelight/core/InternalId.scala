package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import play.api.libs.json.JsString

import scala.util.Random

case class InternalId private(id: String) extends Snapshotable {
  override def snapshotInfo: SnapshotInfo = SnapshotInfo("internalId", JsString(id))
}

object InternalId {

  def generate(length: Int): InternalId = {
    val generated = Random.alphanumeric.take(length)
    new InternalId(generated.mkString)
  }

  def generate: InternalId = generate(8)
}
