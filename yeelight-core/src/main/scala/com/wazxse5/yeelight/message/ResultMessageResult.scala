package com.wazxse5.yeelight.message

import play.api.libs.json.{JsArray, JsObject, JsString, JsValue}

sealed trait ResultMessageResult {
  def name: String
  def value: JsValue
}

final case class Result(value: JsArray) extends ResultMessageResult {
  override val name: String = "result"
}

object Result {
  def ok: Result = Result(JsArray(Seq(JsString("ok"))))
}

final case class Error(value: JsObject) extends ResultMessageResult {
  override val name: String = "error"
  def errorCode: Int = (value \ "code").as[Int]
  def errorMessage: String = (value \ "message").as[String]
}
