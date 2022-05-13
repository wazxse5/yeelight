package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.core.YeelightDevice;
import com.wazxse5.yeelight.core.YeelightService;
import com.wazxse5.yeelight.core.command.*;
import com.wazxse5.yeelight.core.valuetype.Rgb;
import com.wazxse5.yeelight.core.valuetype.Rgb$;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainController {
    private YeelightService yeelightService;

    @FXML TableView<YeelightDevice> devices;
    @FXML TableColumn<YeelightDevice, String> deviceId;
    @FXML TableColumn<YeelightDevice, String> deviceModel;
    @FXML TableColumn<YeelightDevice, String> devicePower;
    @FXML TableColumn<YeelightDevice, String> deviceBrightness;
    @FXML TableColumn<YeelightDevice, String> deviceTemperature;

    @FXML TextField tfBrightness;
    @FXML TextField tfTemperature;
    @FXML ColorPicker cpColor;

    public void setYeelightService(YeelightService yeelightService) {
        this.yeelightService = yeelightService;
        this.devices.setItems(yeelightService.devices());
        this.deviceId.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().deviceId().substring(10)));
        this.deviceModel.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().model().value()));
        this.devicePower.setCellValueFactory(p -> p.getValue().state().powerProperty());
        this.deviceBrightness.setCellValueFactory(p -> p.getValue().state().brightnessProperty().asString());
        this.deviceTemperature.setCellValueFactory(p -> p.getValue().state().temperatureProperty().asString());
    }

    @FXML public void search() {
        yeelightService.search();
    }

    @FXML public void toggle() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(Toggle$.MODULE$);
    }

    @FXML public void turnOn() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(SetPower$.MODULE$.on());
    }

    @FXML public void turnOff() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(SetPower$.MODULE$.off());
    }

    @FXML public void setBrightness() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        int brightness = Integer.parseInt(tfBrightness.getText());
        selectedDevice.performCommand(SetBrightness$.MODULE$.apply(brightness));
    }

    @FXML public void decreaseBrightness() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(AdjustBrightness$.MODULE$.apply(-20));
    }

    @FXML public void increaseBrightness() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(AdjustBrightness$.MODULE$.apply(20));
    }

    @FXML public void setTemperature() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        int temperature = Integer.parseInt(tfTemperature.getText());
        selectedDevice.performCommand(SetTemperature$.MODULE$.apply(temperature));
    }

    @FXML public void setColor() {
        YeelightDevice selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        Color color = cpColor.getValue();
        Rgb rgb = Rgb$.MODULE$.apply((int) color.getRed(), (int) color.getGreen(), (int) color.getBlue());
        selectedDevice.performCommand(SetRgb$.MODULE$.apply(rgb));
    }
}
