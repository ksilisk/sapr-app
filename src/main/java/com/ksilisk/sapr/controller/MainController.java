package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.config.SaprBarConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MainController.class);

    private final SaprBarConfig saprBarConfig = SaprBarConfig.getInstance();

    public void preprocessor(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(saprBarConfig.getPreProcessorViewFile().toURI().toURL());
            Parent parent = loader.load();
            PreprocessorController controller = loader.getController();
            Stage preprocessorStage = new StageBuilder()
                    .title("Preprocessor")
                    .scene(new Scene(parent))
                    .modality(Modality.APPLICATION_MODAL)
                    .resizable(false)
                    .build();
            controller.setOnCloseEventHandler(preprocessorStage);
            preprocessorStage.show();
        } catch (Exception e) {
            log.error("Error while process button action (open preprocessor) in Main View. Event: {}", event, e);
            new Alert(Alert.AlertType.ERROR,
                    "Internal Application Error. Can't open Preprocessor. \nTry again or send report to me!",
                    ButtonType.OK).show();
        }
    }

    public void processor(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(saprBarConfig.getProcessorViewFile().toURI().toURL());
            Scene scene = new Scene(loader.load());
            Stage stage = new StageBuilder()
                    .title("processor")
                    .scene(scene)
                    .modality(Modality.APPLICATION_MODAL)
                    .resizable(false)
                    .build();
            stage.show();
        } catch (Exception e) {
            log.error("Error while process button action (open processor) in Main View. Event: {}", event, e);
            new Alert(Alert.AlertType.ERROR,
                    "Internal Application Error. Can't open Processor. \nTry again or send report to me!",
                    ButtonType.OK).show();
        }
    }

    public void postprocessor(MouseEvent event) {
        // TODO implement this
    }
}
