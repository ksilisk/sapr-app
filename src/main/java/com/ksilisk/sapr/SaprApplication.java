package com.ksilisk.sapr;

import com.ksilisk.sapr.config.SaprAppConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.ksilisk.sapr.config.SaprAppConfig.APP_NAME;

public class SaprApplication extends Application {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SaprApplication.class);

    @Override
    public void init() throws Exception {
        log.info("Initialize application");
        SaprAppConfig.load(getParameters().getNamed());
    }

    @Override
    public void start(Stage stage) throws IOException {
        log.info("Starting main view");
        SaprAppConfig config = SaprAppConfig.getInstance();
        Parent parent = FXMLLoader.load(config.getMainViewFile().toURI().toURL());
        Scene scene = new Scene(parent);
        stage.setResizable(false);
        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();
    }
}