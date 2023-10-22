package com.ksilisk.sapr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.handler.ScaleSceneEventHandler;
import com.ksilisk.sapr.payload.*;
import com.ksilisk.sapr.validate.ValidationException;
import com.ksilisk.sapr.validate.Validator;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.stage.Modality;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class PreprocessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorService.class);
    private static final String SAVED_CONSTRUCTION_FILE_NAME = "sapr-bar-construction";
    private static final String CONSTRUCTION_FILE_EXTENSION = ".json";

    private final Validator validator;
    private final ObjectMapper om = new ObjectMapper();
    private final ErrorStageCreator errorStageCreator = new ErrorStageCreator();
    private final ConstructionStorage constructionStorage = ConstructionStorage.INSTANCE;

    public PreprocessorService(Validator validator) {
        this.validator = validator;
    }

    public void createDraw(ConstructionParameters constructionParameters) {
        try {
            prepareParameters(constructionParameters);
            validator.validate(constructionParameters);
            Construction construction = paramsToConstruction(constructionParameters);
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
            errorStageCreator.create(e.getMessage());
        } catch (Exception e) {
            log.error("Error while creating draw", e);
            errorStageCreator.create("Internal Application Error. Try Again!");
        }
    }

    public void safe(ConstructionParameters constructionParameters, File saveDirectory) {
        log.info("Save construction parameters to file. Params: {}. Directory: {}",
                constructionParameters, saveDirectory.getAbsolutePath());
        try {
            validator.validate(constructionParameters);
            String fileName = SAVED_CONSTRUCTION_FILE_NAME +
                    LocalDateTime.now().format(ISO_DATE_TIME) + CONSTRUCTION_FILE_EXTENSION;
            String jsonParams = om.writeValueAsString(constructionParameters);
            Path resultFilePath = saveDirectory.toPath().resolve(fileName);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(resultFilePath)) {
                bufferedWriter.write(jsonParams);
            }
        } catch (ValidationException e) {
            log.error("Validate construction error. Construction params: {}", constructionParameters, e);
            errorStageCreator.create(e.getMessage()).show();
        } catch (Exception e) {
            log.error("Error while safe construction", e);
            errorStageCreator.create("Internal Application Error. Try Again!");
        }
    }

    public void upload(File parametersFile) {
        // todo implement this
    }

    private void prepareParameters(ConstructionParameters parameters) {
        parameters.nodeLoads().sort(Comparator.comparingInt(NodeLoadDTO::getNodeInd));
        parameters.barLoads().sort(Comparator.comparingInt(BarLoadDTO::getBarInd));
    }

    private Construction paramsToConstruction(ConstructionParameters constructionParameters) {
        List<Bar> bars = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        constructionParameters.bars().forEach(bar -> {
            Bar newBar = new Bar();
            newBar.setArea(bar.getArea());
            newBar.setLength(bar.getLength());
            newBar.setXLoad(constructionParameters.barLoads().get(bar.getSpecInd() - 1).getBarQx());
            newBar.setYLoad(constructionParameters.barLoads().get(bar.getSpecInd() - 1).getBarQy());
            newBar.setElasticMod(constructionParameters.barSpecs().get(bar.getSpecInd() - 1).getElasticMod());
            newBar.setPermisVolt(constructionParameters.barSpecs().get(bar.getSpecInd() - 1).getPermisVolt());
            bars.add(newBar);
        });
        constructionParameters.nodeLoads().forEach(node -> {
            Node newNode = new Node();
            newNode.setXLoad(node.getNodeFx());
            newNode.setYLoad(node.getNodeFy());
            nodes.add(newNode);
        });
        return new Construction(bars, nodes, constructionParameters.leftSupport(), constructionParameters.rightSupport());
    }
}
