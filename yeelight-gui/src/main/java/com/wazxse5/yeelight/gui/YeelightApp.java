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

    private final YeelightService yeelightService = new YeelightServiceImpl();

    public static void run() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/MainPanel.fxml"));
            Parent parent = fxmlLoader.load();
            MainPanelController mainController = fxmlLoader.getController();
            mainController.setYeelightService(yeelightService);

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
        yeelightService.exit();
    }
}