package com.wazxse5.api.valuetype

import com.wazxse5.core.exception.EmptyParamException

case object EmptyParam extends Parameter[String] {
  override def value = throw new EmptyParamException

  override val paramName: String = "EmptyParam"

  override def rawValue = throw new EmptyParamException

  override def toJson = throw new EmptyParamException

  override def isEmptyParam: Boolean = true

  override def isValid: Boolean = false
}

