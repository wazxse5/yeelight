package com.wazxse5.yeelight.core.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
  // TODO normalne logowanie
  
  def error(msg: String): Unit = log("ERROR", msg)
  
  def warn(msg: String): Unit = log("WARN ", msg)
  
  def info(msg: String): Unit = log("INFO ", msg)
  
  def debug(msg: String): Unit = log("DEBUG", msg)
  
  private def log(level: String, msg: String): Unit = {
    val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
    println(s"[$now][$level] - $msg")
  }
}
