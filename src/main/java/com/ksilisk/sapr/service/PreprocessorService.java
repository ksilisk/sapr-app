package com.ksilisk.sapr.service;

import com.ksilisk.sapr.payload.ConstructionParameters;
import com.ksilisk.sapr.validate.ValidationException;
import com.ksilisk.sapr.validate.Validator;

public class PreprocessorService {
    private final Validator validator;

    public PreprocessorService(Validator validator) {
        this.validator = validator;
    }

    public Draw createDraw(ConstructionParameters constructionParameters) {
        try {
            validator.validate(constructionParameters);
        } catch (ValidationException e) {
            // todo some
        } catch (Exception e) {
            // todo some
        }
        return null;
    }

    public void safe(ConstructionParameters constructionParameters) {
        try {
            validator.validate(constructionParameters);
        } catch (ValidationException e) {
            // todo some
        } catch (Exception e) {
            // todo some
        }
    }

    public void upload(ConstructionParameters constructionParameters) {

    }

    public Validator getValidator() {
        return validator;
    }
}
