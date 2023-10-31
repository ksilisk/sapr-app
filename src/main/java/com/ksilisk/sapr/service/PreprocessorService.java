package com.ksilisk.sapr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.handler.DrawSceneEventHandler;
import com.ksilisk.sapr.payload.Construction;
import com.ksilisk.sapr.payload.ConstructionParameters;
import com.ksilisk.sapr.payload.Draw;
import com.ksilisk.sapr.validate.ValidationException;
import com.ksilisk.sapr.validate.Validator;
import com.ksilisk.sapr.validate.ValidatorImpl;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class PreprocessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorService.class);
    private static final String SAVING_CONSTRUCTION_FILE_NAME = "sapr-bar-construction";
    private static final File USER_HOME_DIRECTORY = new File(System.getProperty("user.home"));
    private static final String CONSTRUCTION_FILE_EXTENSION = ".json";
    private static PreprocessorService INSTANCE;

    private final Validator validator;
    private final ObjectMapper om = new ObjectMapper();
    private Path currentFilePath;
    private ConstructionParameters lastSavedParameters;

    private PreprocessorService(Validator validator) {
        this.validator = validator;
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
            Scene scene = new Scene(draw);
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
            lastSavedParameters = constructionParameters;
        } catch (ValidationException e) {
            log.error("Validate construction error. Construction params: {}", constructionParameters, e);
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        } catch (Exception e) {
            log.error("Error while safe construction", e);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error. Try Again!", ButtonType.OK).show();
        }
    }

    private Path getNewFilePath(Window currentStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(USER_HOME_DIRECTORY);
        directoryChooser.setTitle("Choose Directory to save construction");
        File chosenDirectory = directoryChooser.showDialog(currentStage);
        if (chosenDirectory != null) {
            String fileName = SAVING_CONSTRUCTION_FILE_NAME + "_" +
                    LocalDateTime.now().format(ISO_DATE_TIME) + CONSTRUCTION_FILE_EXTENSION;
            return chosenDirectory.toPath().resolve(fileName);
        }
        return null;
    }

    public synchronized Optional<ConstructionParameters> upload(Window currentWindow) {
        log.info("Load construction parameters from file");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(USER_HOME_DIRECTORY);
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Json extension filter", Collections.singletonList("*.json")));
        fileChooser.setTitle("Choose File");
        File file = fileChooser.showOpenDialog(currentWindow);
        if (file != null) {
            currentFilePath = file.toPath();
            StringBuilder jsonParams = new StringBuilder();
            try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
                reader.lines().forEach(jsonParams::append);
                ConstructionParameters constructionParameters = om.readValue(jsonParams.toString(), ConstructionParameters.class);
                lastSavedParameters = constructionParameters;
                return Optional.of(constructionParameters);
            } catch (JsonProcessingException e) {
                log.error("Error while pars params from file. Json params: {}", jsonParams, e);
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

    public synchronized boolean compareParameter(ConstructionParameters currentParameter) {
        return lastSavedParameters.equals(currentParameter);
    }

    private void prepareParameters(ConstructionParameters parameters) {
        parameters.nodeLoads().sort(Comparator.comparingInt(NodeLoadDTO::getNodeInd));
        parameters.barLoads().sort(Comparator.comparingInt(BarLoadDTO::getBarInd));
    }
}
