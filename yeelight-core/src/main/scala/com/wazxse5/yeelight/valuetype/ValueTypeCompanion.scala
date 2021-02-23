package com.wazxse5.yeelight.valuetype

trait ValueTypeCompanion {
  def name: String
}

trait PropCompanion extends ValueTypeCompanion {
  def propFgName: String
  def propBgName: String = "@unsupported"
  final def supportsBackground: Boolean = propBgName != "@unsupported"
}

trait ParamCompanion extends ValueTypeCompanion {
  def paramName: String
}

trait PropAndParamCompanion extends PropCompanion with ParamCompanion