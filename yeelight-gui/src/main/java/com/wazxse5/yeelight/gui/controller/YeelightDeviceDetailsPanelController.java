package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.api.command.SetBrightness$;
import com.wazxse5.yeelight.api.command.SetTemperature$;
import com.wazxse5.yeelight.gui.YeelightDeviceGui;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class YeelightDeviceDetailsPanelController {
    private YeelightDeviceGui device;

    @FXML Slider sBrightness;
    @FXML Slider sTemperature;

    @FXML Label lBrightnessPercent;
    @FXML Label lTemperaturePercent;

    @FXML TextField tfDeviceName;


    public void setDevice(YeelightDeviceGui device) {
        this.device = device;
        tfDeviceName.textProperty().bindBidirectional(device.guiNameProperty);

        lBrightnessPercent.textProperty().bind(sBrightness.valueProperty().asString("%.0f").concat("%"));
        sBrightness.valueProperty().bind(device.state().brightnessProperty());
        sBrightness.valueChangingProperty().addListener((o, wasChanging, isChanging) -> {
            if (!wasChanging && isChanging) {
                sBrightness.valueProperty().unbind();
            } else if (wasChanging && !isChanging) {
                int newBrightness = (int) sBrightness.getValue();
                device.performCommand(SetBrightness$.MODULE$.apply(newBrightness));
                delay(500, () -> sBrightness.valueProperty().bind(device.state().brightnessProperty()));
            }
        });

        lTemperaturePercent.textProperty().bind(sTemperature.valueProperty().asString("%.0f").concat("°C"));
        sTemperature.valueProperty().bind(device.state().temperatureProperty());
        sTemperature.valueChangingProperty().addListener((o, wasChanging, isChanging) -> {
            if (!wasChanging && isChanging) {
                sTemperature.valueProperty().unbind();
            } else if (wasChanging && !isChanging) {
                int newTemperature = (int) sTemperature.getValue();
                device.performCommand(SetTemperature$.MODULE$.apply(newTemperature));
                delay(500, () -> sTemperature.valueProperty().bind(device.state().temperatureProperty()));
            }
        });
    }

    private void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ignored) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}
