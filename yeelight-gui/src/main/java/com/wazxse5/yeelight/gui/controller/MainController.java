package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.api.*;
import com.wazxse5.yeelight.api.command.*;
import com.wazxse5.yeelight.api.valuetype.Rgb;
import com.wazxse5.yeelight.api.valuetype.Rgb$;
import com.wazxse5.yeelight.gui.YeelightDeviceGui;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class MainController {
    private YeelightService yeelightService;

    @FXML TableView<YeelightDeviceGui> devices;
    @FXML TableColumn<YeelightDeviceGui, String> deviceId;
    @FXML TableColumn<YeelightDeviceGui, String> deviceModel;
    @FXML TableColumn<YeelightDeviceGui, String> devicePower;
    @FXML TableColumn<YeelightDeviceGui, String> deviceBrightness;
    @FXML TableColumn<YeelightDeviceGui, String> deviceTemperature;

    @FXML TextField tfBrightness;
    @FXML TextField tfTemperature;
    @FXML ColorPicker cpColor;

    private final ObservableList<YeelightDeviceGui> devicesList = FXCollections.observableArrayList();

    public void setYeelightService(YeelightService yeelightService) {
        this.yeelightService = yeelightService;
        this.devices.setItems(devicesList);
        this.yeelightService.addEventListener(event -> {
                if (event instanceof DeviceAdded) {
                    YeelightDevice addedDevice = yeelightService.devicesJava().get(event.deviceId());
                    YeelightDeviceGui addedDeviceGui = new YeelightDeviceGui(addedDevice);
                    devicesList.add(addedDeviceGui);
                } else if (event instanceof DeviceUpdated) {
                    devicesList.filtered(device -> device.deviceId().equals(event.deviceId()))
                        .forEach(YeelightDeviceGui::refresh);
                }
            }
        );


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
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(Toggle$.MODULE$);
    }

    @FXML public void turnOn() {
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(SetPower$.MODULE$.on());
    }

    @FXML public void turnOff() {
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(SetPower$.MODULE$.off());
    }

    @FXML public void setBrightness() {
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        int brightness = Integer.parseInt(tfBrightness.getText());
        selectedDevice.performCommand(SetBrightness$.MODULE$.apply(brightness));
    }

    @FXML public void decreaseBrightness() {
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(AdjustBrightness$.MODULE$.apply(-20));
    }

    @FXML public void increaseBrightness() {
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        selectedDevice.performCommand(AdjustBrightness$.MODULE$.apply(20));
    }

    @FXML public void setTemperature() {
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        int temperature = Integer.parseInt(tfTemperature.getText());
        selectedDevice.performCommand(SetTemperature$.MODULE$.apply(temperature));
    }

    @FXML public void setColor() {
        YeelightDeviceGui selectedDevice = devices.selectionModelProperty().getValue().getSelectedItem();
        Color color = cpColor.getValue();
        Rgb rgb = Rgb$.MODULE$.apply((int) color.getRed(), (int) color.getGreen(), (int) color.getBlue());
        selectedDevice.performCommand(SetRgb$.MODULE$.apply(rgb));
    }
}
