package com.wazxse5.yeelight.core

import javafx.beans.property.{ReadOnlyBooleanProperty, ReadOnlyIntegerProperty}

trait YeelightState {
  
  def isConnectedProperty: ReadOnlyBooleanProperty
  
  def brightnessProperty: ReadOnlyIntegerProperty
  
  def temperatureProperty: ReadOnlyIntegerProperty
}
