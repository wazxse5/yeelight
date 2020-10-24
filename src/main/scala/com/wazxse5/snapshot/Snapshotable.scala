package com.wazxse5.snapshot

import play.api.libs.json.{JsObject, JsValue, Json}

trait Snapshotable {
  def snapshotInfo: SnapshotInfo
}

case class SnapshotInfo(name: String, value: JsValue) {
  def pair: (String, JsValue) = name -> value

  def objectValue: JsObject = Json.obj(
    "snapshotName"-> name,
    "value" -> value
  )
}