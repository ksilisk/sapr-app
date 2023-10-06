package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.config.SaprBarConfig;
import com.ksilisk.sapr.service.StageBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    private final SaprBarConfig saprBarConfig = SaprBarConfig.getInstance();

    @FXML
    private Button preProcessor;

    @FXML
    private Button processor;

    @FXML
    private Button postProcessor;

    @FXML
    protected void preProcessor() {
        try {
            Parent parent = FXMLLoader.load(saprBarConfig.getPreProcessorViewFile().toURI().toURL());
            Stage currStage = (Stage) preProcessor.getScene().getWindow();
            currStage.hide();
            Stage stage = new StageBuilder().resizable(false)
                    .modality(Modality.WINDOW_MODAL)
                    .title("Препроцессор")
                    .scene(new Scene(parent, 600, 600))
                    .build();
            stage.setOnCloseRequest(event -> currStage.show());
            currStage.hide();
            stage.show();
        } catch (Exception e) {
            throw new IllegalStateException("Error while create view for preprocessor", e);
        }
    }

    @FXML
    protected void processor() {
        try {
            Parent parent = FXMLLoader.load(saprBarConfig.getProcessorViewFile().toURI().toURL());
            Stage currStage = (Stage) processor.getScene().getWindow();
            currStage.hide();
            Stage stage = new StageBuilder().resizable(false)
                    .modality(Modality.WINDOW_MODAL)
                    .title("Процессор")
                    .scene(new Scene(parent, 400, 400))
                    .build();
            stage.setOnCloseRequest(event -> currStage.show());
            currStage.hide();
            stage.show();
        } catch (Exception e) {
            throw new IllegalStateException("Error while create view for processor", e);
        }
    }

    @FXML
    protected void postProcessor() {
        try {
            Parent parent = FXMLLoader.load(saprBarConfig.getPostProcessorViewFile().toURI().toURL());
            Stage currStage = (Stage) postProcessor.getScene().getWindow();
            currStage.hide();
            Stage stage = new StageBuilder().resizable(false)
                    .modality(Modality.WINDOW_MODAL)
                    .title("Постпроцессор")
                    .scene(new Scene(parent, 400, 400))
                    .build();
            stage.setOnCloseRequest(event -> currStage.show());
            currStage.hide();
            stage.show();
        } catch (Exception e) {
            throw new IllegalStateException("Error while create view for postprocessor", e);
        }
    }
}
