package com.ksilisk.sapr.service;

import com.ksilisk.sapr.builder.StageBuilder;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorStageCreator {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorService.class);
    private static final int ERROR_SCENE_WIDTH = 400;
    private static final int ERROR_SCENE_HEIGHT = 100;

    public Stage create(String errorMessage) {
        Label label = new Label(errorMessage);
        VBox vBox = new VBox(label);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinSize(ERROR_SCENE_WIDTH, ERROR_SCENE_HEIGHT);
        return new StageBuilder().scene(new Scene(vBox))
                .resizable(false)
                .build();
    }
}
