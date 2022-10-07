package com.wazxse5.yeelight.core.util

object Implicits {
  implicit class ReturnNone(val a: Any) extends AnyVal {
    def returnNone: None.type = None

    def andReturn[A](a: A) = a
  }

}
