package com.ksilisk.sapr.validate;

import com.ksilisk.sapr.payload.ConstructionParameters;

public interface Validator {
    void validate(ConstructionParameters constructionParameters);
}
