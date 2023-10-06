package com.ksilisk.sapr;

import com.ksilisk.sapr.config.SaprBarConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SaprApp extends Application {
    @Override
    public void init() throws Exception {
        SaprBarConfig.load(getParameters().getNamed());
    }

    @Override
    public void start(Stage stage) throws IOException {
        SaprBarConfig instance = SaprBarConfig.getInstance();
        Scene scene = new Scene(FXMLLoader.load(instance.getMainViewFile().toURI().toURL()), 400, 400);
        stage.setResizable(false);
        stage.setTitle(SaprBarConfig.APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}