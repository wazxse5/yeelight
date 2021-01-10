package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.exception.EmptyParamException

case object EmptyParam extends Parameter[Nothing] with ParamCompanion {
  override def companion: ParamCompanion = this

  override def value = throw new EmptyParamException

  override val paramName: String = "emptyParam"
  override val snapshotName: String = "emptyParam"

  override def strValue = throw new EmptyParamException

  override def paramValue = throw new EmptyParamException

  override def isEmptyParam: Boolean = true

  override def isValid: Boolean = false
}

