package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.config.SaprBarConfig;
import com.ksilisk.sapr.service.StageBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final SaprBarConfig saprBarConfig = SaprBarConfig.getInstance();
    private final Map<String, File> map = new HashMap<>();

    @FXML
    private Button preProcessor, processor, postProcessor;

    @FXML
    public void process(MouseEvent event) {
        try {
            Button button = (Button) event.getSource();
            Parent parent = FXMLLoader.load(map.get(button.getId()).toURI().toURL());
            Stage currentStage = (Stage) button.getScene().getWindow();
            Stage newStage = new StageBuilder()
                    .resizable(true)
                    .title(button.getText())
                    .modality(Modality.WINDOW_MODAL)
                    .scene(new Scene(parent))
                    .build();
            currentStage.hide();
            newStage.show();
            newStage.setOnCloseRequest(e -> currentStage.show());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        map.put(preProcessor.getId(), saprBarConfig.getPreProcessorViewFile());
        map.put(processor.getId(), saprBarConfig.getProcessorViewFile());
        map.put(postProcessor.getId(), saprBarConfig.getPostProcessorViewFile());
    }
}
