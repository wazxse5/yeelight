package com.wazxse5.yeelight.core

import javafx.beans.property.{ReadOnlyBooleanProperty, ReadOnlyIntegerProperty, ReadOnlyStringProperty}

trait YeelightState {
  
  def isConnectedProperty: ReadOnlyBooleanProperty
  
  def powerProperty: ReadOnlyStringProperty
  
  def brightnessProperty: ReadOnlyIntegerProperty
  
  def temperatureProperty: ReadOnlyIntegerProperty
}
