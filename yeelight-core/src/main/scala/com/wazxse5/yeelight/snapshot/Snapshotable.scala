package com.wazxse5.yeelight.snapshot

import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json.{JsObject, JsValue, Json}

trait Snapshotable {
  def snapshotInfo: SnapshotInfo
}

case class SnapshotInfo(name: String, value: JsValue) {
  def pair: (String, JsValue) = name -> value

  def pairw: (String, JsValueWrapper) = name -> value

  def objectValue: JsObject = Json.obj(
    "snapshotName"-> name,
    "value" -> value
  )
}