package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.api.command.SetBrightness$;
import com.wazxse5.yeelight.api.command.SetRgb$;
import com.wazxse5.yeelight.api.command.SetTemperature$;
import com.wazxse5.yeelight.api.valuetype.Rgb;
import com.wazxse5.yeelight.gui.YeelightDeviceGui;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class YeelightDeviceDetailsPanelController {
    private YeelightDeviceGui device;

    @FXML Slider sBrightness;
    @FXML Slider sTemperature;
    @FXML ColorPicker colorPicker;

    @FXML Label lBrightnessPercent;
    @FXML Label lTemperaturePercent;

    @FXML TextField tfDeviceName;

    private Task<Void> sliderBrightnessBinderTask;
    private Task<Void> sliderTemperatureBinderTask;
    private Task<Void> colorPickerBinderTask;


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
                rebindSliderBrightness();
            }
        });

        lTemperaturePercent.textProperty().bind(sTemperature.valueProperty().asString("%.0f").concat("K"));
        sTemperature.valueProperty().bind(device.state().temperatureProperty());
        sTemperature.valueChangingProperty().addListener((o, wasChanging, isChanging) -> {
            if (!wasChanging && isChanging) {
                sTemperature.valueProperty().unbind();
            } else if (wasChanging && !isChanging) {
                int newTemperature = (int) sTemperature.getValue();
                device.performCommand(SetTemperature$.MODULE$.apply(newTemperature));
                rebindSliderTemperature();
            }
        });

        colorPicker.valueProperty().bind(device.state().colorProperty());
        colorPicker.setOnAction(e -> {
            sendSetRgbCommand();
            rebindColorPicker();
        });
        colorPicker.setOnMouseClicked(e -> colorPicker.valueProperty().unbind());
        colorPicker.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (!wasFocused && isFocused) {
                colorPicker.valueProperty().unbind();
            } else if (wasFocused && !isFocused) {
                rebindColorPicker();
            }
        });
    }

    private void rebindSliderBrightness() {
        if (sliderBrightnessBinderTask != null) sliderBrightnessBinderTask.cancel();
        sliderBrightnessBinderTask = delayTask(500);
        sliderBrightnessBinderTask.setOnSucceeded(e -> sBrightness.valueProperty().bind(device.state().brightnessProperty()));
        new Thread(sliderBrightnessBinderTask).start();
    }

    private void rebindSliderTemperature() {
        if (sliderTemperatureBinderTask != null) sliderTemperatureBinderTask.cancel();
        sliderTemperatureBinderTask = delayTask(500);
        sliderTemperatureBinderTask.setOnSucceeded(e -> sTemperature.valueProperty().bind(device.state().temperatureProperty()));
        new Thread(sliderTemperatureBinderTask).start();
    }

    private void rebindColorPicker() {
        if (colorPickerBinderTask != null) colorPickerBinderTask.cancel();
        colorPickerBinderTask = delayTask(500);
        colorPickerBinderTask.setOnSucceeded(e -> colorPicker.valueProperty().bind(device.state().colorProperty()));
        new Thread(colorPickerBinderTask).start();
    }

    public void sendSetRgbCommand() {
        Color color = colorPicker.getValue();
        int red = (int) (color.getRed() * 255);
        int green = (int) (color.getGreen() * 255);
        int blue = (int) (color.getBlue() * 255);
        Rgb rgb = Rgb.apply(red, green, blue);
        device.performCommand(SetRgb$.MODULE$.apply(rgb));
    }

    private Task<Void> delayTask(long millis) {
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException ignored) {}
                return null;
            }
        };
    }
}
