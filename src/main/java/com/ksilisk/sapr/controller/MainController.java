package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.config.SaprBarConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MainController.class);

    private final SaprBarConfig saprBarConfig = SaprBarConfig.getInstance();

    @FXML
    private Button preProcessor, processor, postProcessor;

    public void preprocessor(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(saprBarConfig.getPreProcessorViewFile().toURI().toURL());
            Stage preprocessorStage = new StageBuilder()
                    .title("Preprocessor")
                    .scene(new Scene(parent))
                    .modality(Modality.WINDOW_MODAL)
                    .build();
            preprocessorStage.show();
        } catch (Exception e) {
            log.error("Error while process button action in Main View. Action: {}", event.getSource(), e);
        }
    }

    public void processor(MouseEvent event) {

    }

    public void postprocessor(MouseEvent event) {

    }

//    public void process(MouseEvent event) {
//        try {
//            Button button = (Button) event.getSource();
//            Parent parent = FXMLLoader.load(viewsMap.get(button.getId()).toURI().toURL());
//            Stage currentStage = (Stage) button.getScene().getWindow();
//            Stage newStage = new StageBuilder()
//                    .title(button.getText())
//                    .modality(Modality.WINDOW_MODAL)
//                    .scene(new Scene(parent))
//                    .build();
//            currentStage.hide();
//            newStage.show();
//            newStage.setOnCloseRequest(e -> currentStage.show());
//        } catch (Exception e) {
//
//        }
//    }
}
