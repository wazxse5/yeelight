package com.wazxse5.yeelight.gui;

import com.wazxse5.yeelight.api.YeelightService;
import com.wazxse5.yeelight.core.YeelightServiceImpl;
import com.wazxse5.yeelight.core.util.Logger;
import com.wazxse5.yeelight.gui.controller.MainPanelController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            YeelightGuiAppData guiAppData = YeelightGuiAppData$.MODULE$.read();
            yeelightService = new YeelightServiceImpl(guiAppData.appData());

            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/MainPanel.fxml"));
            Parent parent = fxmlLoader.load();
            mainPanelController = fxmlLoader.getController();
            mainPanelController.initialize(yeelightService, guiAppData);

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
        YeelightGuiAppData appData = YeelightGuiAppData$.MODULE$.apply(
            yeelightService.getAppData(),
            mainPanelController.getDevicesGuiNames()
        );
        YeelightGuiAppData$.MODULE$.write(appData);
        yeelightService.exit();
    }
}