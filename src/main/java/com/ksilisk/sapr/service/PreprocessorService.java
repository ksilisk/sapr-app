package com.ksilisk.sapr.service;

import com.ksilisk.sapr.payload.ConstructionParameters;
import com.ksilisk.sapr.validate.ValidationException;
import com.ksilisk.sapr.validate.Validator;
import javafx.stage.Stage;

public class PreprocessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorService.class);

    private final Validator validator;
    private final ErrorStageCreator errorStageCreator = new ErrorStageCreator();

    public PreprocessorService(Validator validator) {
        this.validator = validator;
    }

    public Stage createDraw(ConstructionParameters constructionParameters) {
        try {
            validator.validate(constructionParameters);
            return null;
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

    }

    public Validator getValidator() {
        return validator;
    }
}
