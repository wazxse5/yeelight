package com.wazxse5.yeelight.core.util

import javafx.collections.{FXCollections, MapChangeListener, ObservableList, ObservableMap}

class ObservableListMapBinder[K, V](map: ObservableMap[K, V]) extends MapChangeListener[K, V] {
  
  private val list = FXCollections.observableArrayList(map.values())
  
  map.addListener(this)
  
  def getList: ObservableList[V] = FXCollections.unmodifiableObservableList(list)
  
  override def onChanged(change: MapChangeListener.Change[_ <: K, _ <: V]): Unit = {
    if (change.wasRemoved()) {
      list.remove(change.getValueRemoved)
    }
    if (change.wasAdded()) {
      list.add(change.getValueAdded)
    }
  }
}