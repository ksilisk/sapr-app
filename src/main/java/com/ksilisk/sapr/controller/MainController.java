package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.config.SaprBarConfig;
import com.ksilisk.sapr.service.ProcessorService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MainController.class);

    private final SaprBarConfig saprBarConfig = SaprBarConfig.getInstance();
    private final ProcessorService processorService = ProcessorService.getInstance();

    public void preprocessor(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(saprBarConfig.getPreProcessorViewFile().toURI().toURL());
            Parent parent = loader.load();
            PreprocessorController controller = loader.getController();
            Stage preprocessorStage = new StageBuilder()
                    .title("Preprocessor")
                    .scene(new Scene(parent))
                    .modality(Modality.WINDOW_MODAL)
                    .build();
            controller.setOnCloseEventHandler(preprocessorStage);
            preprocessorStage.show();
        } catch (Exception e) {
            log.error("Error while process button action in Main View. Action: {}", event.getSource(), e);
        }
    }

    public void processor() {
        try {
            FXMLLoader loader = new FXMLLoader(saprBarConfig.getProcessorViewFile().toURI().toURL());
            Scene scene = new Scene(loader.load(), saprBarConfig.getProcessorViewWidth(), saprBarConfig.getProcessorViewHeight());
            Stage stage = new StageBuilder()
                    .title("processor")
                    .scene(scene)
                    .modality(Modality.WINDOW_MODAL)
                    .resizable(true)
                    .build();
            stage.show();
        } catch (Exception e) {
            log.error("Some error", e);
        }
    }

    public void postprocessor(MouseEvent event) {

    }
}
