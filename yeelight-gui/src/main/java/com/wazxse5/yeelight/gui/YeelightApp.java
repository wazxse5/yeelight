package com.wazxse5.yeelight.gui;

import com.wazxse5.yeelight.api.YeelightService;
import com.wazxse5.yeelight.core.YeelightServiceImpl;
import com.wazxse5.yeelight.core.util.Logger;
import com.wazxse5.yeelight.gui.controller.MainPanelController;
import com.wazxse5.yeelight.gui.data.YeelightAppData;
import com.wazxse5.yeelight.gui.data.YeelightAppData$;
import com.wazxse5.yeelight.gui.data.YeelightKnownDeviceGui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scala.collection.Iterable;

import java.io.IOException;

public class YeelightApp extends Application {

    private YeelightService yeelightService;
    private MainPanelController mainPanelController;

    public static void run() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            YeelightAppData appData = YeelightAppData$.MODULE$.read();
            Iterable<YeelightKnownDeviceGui> knownDevices = appData.devices().map(YeelightKnownDeviceGui::toKnownDevice);
            yeelightService = new YeelightServiceImpl(knownDevices.toSet());

            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/MainPanel.fxml"));
            Parent parent = fxmlLoader.load();
            mainPanelController = fxmlLoader.getController();
            mainPanelController.setYeelightService(yeelightService, appData);

            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Yeelight");
            stage.show();
        } catch (IOException e) {
            Logger.error("Cannot load MainPanel.fxml file: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        YeelightAppData appData = mainPanelController.getYeelightAppData();
        YeelightAppData$.MODULE$.write(appData);
        yeelightService.exit();
    }
}