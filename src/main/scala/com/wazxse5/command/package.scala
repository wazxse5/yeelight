package com.wazxse5

import com.wazxse5.valuetype.{EmptyParam, Parameter, PropertyName}

package object command {
  implicit def paramToOpt(param: Parameter[_]): Option[Parameter[_]] = Option(param)

  implicit def optToParam(optParam: Option[Parameter[_]]): Parameter[_] = optParam.getOrElse(EmptyParam)

  implicit def stringToOptPropertyName(string: String): Option[PropertyName] = Option(PropertyName(string))

  implicit def stringToPropertyName(string: String): PropertyName = PropertyName(string)
}
