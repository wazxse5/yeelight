package com.wazxse5.yeelight.gui;

import com.wazxse5.yeelight.api.YeelightState;
import com.wazxse5.yeelight.api.valuetype.Rgb;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class YeelightStateGui {

    private final SimpleBooleanProperty _isConnectedProperty;
    private final SimpleBooleanProperty _isOnProperty;
    private final SimpleStringProperty _powerProperty;
    private final SimpleIntegerProperty _brightnessProperty;
    private final SimpleIntegerProperty _temperatureProperty;
    private final SimpleObjectProperty<Color> _colorProperty;

    public YeelightStateGui(YeelightState yeelightState) {
        _isConnectedProperty = new SimpleBooleanProperty(yeelightState.isConnected());
        _isOnProperty = new SimpleBooleanProperty();
        _powerProperty = new SimpleStringProperty(yeelightState.power().value());
        _brightnessProperty = new SimpleIntegerProperty(yeelightState.brightness().value());
        _temperatureProperty = new SimpleIntegerProperty(yeelightState.temperature().value());
        _colorProperty = new SimpleObjectProperty<>(rgbToColor(yeelightState.rgb()));

        _isOnProperty.bind(_powerProperty.isEqualTo("on"));
    }

    public ReadOnlyBooleanProperty isConnectedProperty() {
        return _isConnectedProperty;
    }

    public ReadOnlyBooleanProperty isOnProperty() {
        return _isOnProperty;
    }

    public ReadOnlyStringProperty powerProperty() {
        return _powerProperty;
    }

    public ReadOnlyIntegerProperty brightnessProperty() {
        return _brightnessProperty;
    }

    public ReadOnlyIntegerProperty temperatureProperty() {
        return _temperatureProperty;
    }

    public ReadOnlyObjectProperty<Color> colorProperty() {
        return _colorProperty;
    }

    public void update(YeelightState newState) {
        _isConnectedProperty.set(newState.isConnected());
        _powerProperty.set(newState.power().value());
        _brightnessProperty.set(newState.brightness().value());
        _temperatureProperty.set(newState.temperature().value());
        _colorProperty.set(rgbToColor(newState.rgb()));
    }

    private Color rgbToColor(Rgb rgb) {
        return Color.rgb(rgb.red(), rgb.green(), rgb.blue());
    }
}
