package com.ksilisk.sapr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.config.SaprBarConfig;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.handler.DrawSceneEventHandler;
import com.ksilisk.sapr.payload.Construction;
import com.ksilisk.sapr.payload.ConstructionParameters;
import com.ksilisk.sapr.payload.Draw;
import com.ksilisk.sapr.storage.ConstructionStorage;
import com.ksilisk.sapr.validate.ValidationException;
import com.ksilisk.sapr.validate.Validator;
import com.ksilisk.sapr.validate.ValidatorImpl;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.ksilisk.sapr.config.SaprBarConfig.USER_HOME_DIRECTORY;

public class PreprocessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorService.class);
    private static final List<String> CONSTRUCTION_FILE_EXTENSIONS = List.of("*.json");
    private static PreprocessorService INSTANCE;

    private final SaprBarConfig saprBarConfig = SaprBarConfig.getInstance();
    private final ObjectMapper om = new ObjectMapper();
    private final FileChooser fileChooser = new FileChooser();
    private final ConstructionStorage storage = ConstructionStorage.INSTANCE;
    private final Validator validator;
    private Path currentFilePath;

    private PreprocessorService(Validator validator) {
        this.validator = validator;
        fileChooser.setInitialDirectory(USER_HOME_DIRECTORY);
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Construction file extensions", CONSTRUCTION_FILE_EXTENSIONS));
        fileChooser.setTitle("Choose File");
    }

    public void createDraw(ConstructionParameters constructionParameters) {
        try {
            prepareParameters(constructionParameters);
            validator.validate(constructionParameters);
            Construction construction = Construction.fromParameters(constructionParameters);
            Draw draw = Draw.builder()
                    .bars(construction.bars())
                    .nodes(construction.nodes())
                    .leftSupport(construction.leftSupport())
                    .rightSupport(construction.rightSupport())
                    .build();
            Camera camera = new ParallelCamera();
            Scene scene = new Scene(draw, saprBarConfig.getPreprocessorDrawWidth(), saprBarConfig.getPreprocessorDrawHeight());
            scene.setCamera(camera);
            scene.setOnKeyPressed(new DrawSceneEventHandler());
            new StageBuilder()
                    .title("Construction")
                    .modality(Modality.WINDOW_MODAL)
                    .scene(scene)
                    .build().show();
        } catch (ValidationException e) {
            log.error("Validate construction error. Construction params: {}", constructionParameters, e);
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (Exception e) {
            log.error("Error while creating draw", e);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error. Try Again!", ButtonType.OK).show();
        }
    }

    public synchronized void save(ConstructionParameters constructionParameters, Window currentWindow) {
        log.info("Save construction parameters to file. Params: {}", constructionParameters);
        try {
            prepareParameters(constructionParameters);
            validator.validate(constructionParameters);
            if (currentFilePath == null) {
                log.info("Path to current file not present. Get new path.");
                Path newFilePath = getNewFilePath(currentWindow);
                if (newFilePath == null) {
                    return;
                }
                currentFilePath = newFilePath;
            }
            String jsonParams = om.writeValueAsString(constructionParameters);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(currentFilePath)) {
                bufferedWriter.write(jsonParams);
            }
            storage.setParameters(constructionParameters);
        } catch (ValidationException e) {
            log.error("Validate construction error. Construction params: {}", constructionParameters, e);
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (Exception e) {
            log.error("Error while safe construction", e);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error. Try Again!", ButtonType.OK).show();
        }
    }

    private Path getNewFilePath(Window currentStage) {
        File chosenFile = fileChooser.showSaveDialog(currentStage);
        if (chosenFile != null) {
            return chosenFile.toPath();
        }
        return null;
    }

    public synchronized Optional<ConstructionParameters> upload(Window currentWindow) {
        log.info("Try upload construction parameters");
        File chosenFile = fileChooser.showOpenDialog(currentWindow);
        if (chosenFile != null) {
            currentFilePath = chosenFile.toPath();
            log.info("Upload parameters from file. File path: {}", currentFilePath);
            StringBuilder jsonParams = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(chosenFile.toPath())) {
                reader.lines().forEach(jsonParams::append);
                ConstructionParameters constructionParameters = om.readValue(jsonParams.toString(), ConstructionParameters.class);
                storage.setParameters(constructionParameters);
                return Optional.of(constructionParameters);
            } catch (JsonProcessingException e) {
                log.error("Error while parsing params from file. Loaded params: {}", jsonParams, e);
                new Alert(Alert.AlertType.ERROR, "File is invalid. Parsing error.", ButtonType.OK).show();
            } catch (IOException e) {
                log.error("Error while read params from file.", e);
                new Alert(Alert.AlertType.ERROR, "Internal Application Error. Try Again!", ButtonType.OK).show();
            }
        }
        return Optional.empty();
    }

    public static synchronized PreprocessorService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PreprocessorService(new ValidatorImpl());
        }
        return INSTANCE;
    }

    private void prepareParameters(ConstructionParameters parameters) {
        parameters.nodeLoads().sort(Comparator.comparingInt(NodeLoadDTO::getNodeInd));
        parameters.barLoads().sort(Comparator.comparingInt(BarLoadDTO::getBarInd));
    }
}
