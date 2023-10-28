package com.ksilisk.sapr;

import com.ksilisk.sapr.config.SaprBarConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.ksilisk.sapr.config.SaprBarConfig.APP_NAME;

public class SaprApp extends Application {
    //1. Модуль и допускаемое напряжение больше нуля (fixed)
//2. Длина и поперечное сечение больше нуля (fixed)
//3. Как минимум одна опора (fixed)
//4. Масштабирование (fixed)
//5. Корректная валидация нумерации (fixed)
//6. Добавить запрос на закрытие без сохранения
//7. Добавить отрисовку qx для стержня (fixed)
//7. Убрать fy, qy (fixed)
//8. Исправить опечатки (fixed)
    @Override
    public void init() throws Exception {
        SaprBarConfig.load(getParameters().getNamed());
    }

    @Override
    public void start(Stage stage) throws IOException {
        SaprBarConfig config = SaprBarConfig.getInstance();
        Parent parent = FXMLLoader.load(config.getMainViewFile().toURI().toURL());
        Scene scene = new Scene(parent);
        stage.setResizable(false);
        stage.setTitle(APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}