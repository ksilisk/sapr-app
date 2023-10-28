package com.ksilisk.sapr.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.handler.ScaleSceneEventHandler;
import com.ksilisk.sapr.payload.Construction;
import com.ksilisk.sapr.payload.ConstructionParameters;
import com.ksilisk.sapr.payload.Draw;
import com.ksilisk.sapr.validate.ValidationException;
import com.ksilisk.sapr.validate.Validator;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class PreprocessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorService.class);
    private static final String SAVING_CONSTRUCTION_FILE_NAME = "sapr-bar-construction";
    private static final String CONSTRUCTION_FILE_EXTENSION = ".json";

    private final Validator validator;
    private final ObjectMapper om = new ObjectMapper();
    private final ConstructionStorage constructionStorage = ConstructionStorage.INSTANCE;

    public PreprocessorService(Validator validator) {
        this.validator = validator;
    }

    public void createDraw(ConstructionParameters constructionParameters) {
        try {
            prepareParameters(constructionParameters);
            validator.validate(constructionParameters);
            Construction construction = Construction.fromParameters(constructionParameters);
            constructionStorage.setConstruction(construction);
            Draw draw = Draw.builder()
                    .bars(construction.bars())
                    .nodes(construction.nodes())
                    .leftSupport(construction.leftSupport())
                    .rightSupport(construction.rightSupport())
                    .width(700)
                    .height(500)
                    .build();
            Camera camera = new ParallelCamera();
            Scene scene = new Scene(draw, 700, 500);
            scene.setCamera(camera);
            scene.setOnKeyPressed(new ScaleSceneEventHandler());
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

    public void safe(ConstructionParameters constructionParameters, File saveDirectory) {
        log.info("Save construction parameters to file. Params: {}. Directory: {}",
                constructionParameters, saveDirectory.getAbsolutePath());
        try {
            prepareParameters(constructionParameters);
            validator.validate(constructionParameters);
            String fileName = SAVING_CONSTRUCTION_FILE_NAME + "_" +
                    LocalDateTime.now().format(ISO_DATE_TIME) + CONSTRUCTION_FILE_EXTENSION;
            String jsonParams = om.writeValueAsString(constructionParameters);
            Path resultFilePath = saveDirectory.toPath().resolve(fileName);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(resultFilePath)) {
                bufferedWriter.write(jsonParams);
            }
        } catch (ValidationException e) {
            log.error("Validate construction error. Construction params: {}", constructionParameters, e);
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
        } catch (Exception e) {
            log.error("Error while safe construction", e);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error. Try Again!", ButtonType.OK).show();
        }
    }

    public Optional<ConstructionParameters> upload(File parametersFile) {
        log.info("Load construction parameters from file. File path: {}", parametersFile.getAbsolutePath());
        StringBuilder jsonParams = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(parametersFile.toPath())) {
            reader.lines().forEach(jsonParams::append);
            return Optional.of(om.readValue(jsonParams.toString(), ConstructionParameters.class));
        } catch (JsonProcessingException e) {
            log.error("Error while pars params from file. Json params: {}", jsonParams, e);
            new Alert(Alert.AlertType.ERROR, "File is invalid. Parsing error.", ButtonType.OK).show();
        } catch (IOException e) {
            log.error("Error while read params from file.", e);
            new Alert(Alert.AlertType.ERROR, "Internal Application Error. Try Again!", ButtonType.OK).show();
        }
        return Optional.empty();
    }

    private void prepareParameters(ConstructionParameters parameters) {
        parameters.nodeLoads().sort(Comparator.comparingInt(NodeLoadDTO::getNodeInd));
        parameters.barLoads().sort(Comparator.comparingInt(BarLoadDTO::getBarInd));
    }
}
