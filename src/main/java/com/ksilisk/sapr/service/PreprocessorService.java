package com.ksilisk.sapr.service;

import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.payload.*;
import com.ksilisk.sapr.validate.ValidationException;
import com.ksilisk.sapr.validate.Validator;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PreprocessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorService.class);

    private final Validator validator;
    private final ErrorStageCreator errorStageCreator = new ErrorStageCreator();
    private final ConstructionStorage constructionStorage = ConstructionStorage.INSTANCE;

    public PreprocessorService(Validator validator) {
        this.validator = validator;
    }

    public Stage createDraw(ConstructionParameters constructionParameters) {
        try {
            prepareParameters(constructionParameters);
            validator.validate(constructionParameters);
            Construction construction = paramsToConstruction(constructionParameters);
            Stage stage = new Stage();
            Draw draw = Draw.builder()
                    .bars(construction.bars())
                    .nodes(construction.nodes())
                    .leftSupport(construction.leftSupport())
                    .rightSupport(construction.rightSupport())
                    .width(700)
                    .height(500)
                    .build();
            stage.setScene(new Scene(draw, 700, 500));
            return stage;
        } catch (ValidationException e) {
            log.error("Validate construction error. Construction params: {}", constructionParameters, e);
            return errorStageCreator.create(e.getMessage());
        } catch (Exception e) {
            log.error("Error while creating draw", e);
            return errorStageCreator.create(e.getMessage());
        }
    }

    public void safe(ConstructionParameters constructionParameters) {
        try {
            // todo saving data
        } catch (ValidationException e) {
            log.error("Validate construction error. Construction params: {}", constructionParameters, e);
        } catch (Exception e) {
            log.error("Error while safe construction", e);
        }
    }

    public void upload(ConstructionParameters constructionParameters) {
        // todo implement this
    }

    private ConstructionParameters prepareParameters(ConstructionParameters parameters) {
        parameters.nodeLoads().sort(Comparator.comparingInt(NodeLoadDTO::getNodeInd));
        parameters.barLoads().sort(Comparator.comparingInt(BarLoadDTO::getBarInd));
        return parameters;
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

    public Validator getValidator() {
        return validator;
    }
}
