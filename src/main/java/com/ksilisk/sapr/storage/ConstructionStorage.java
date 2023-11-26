package com.ksilisk.sapr.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksilisk.sapr.payload.ConstructionParameters;

public enum ConstructionStorage {
    INSTANCE;

    private final ObjectMapper om = new ObjectMapper();
    private ConstructionParameters parameters;

    public synchronized ConstructionParameters getParameters() {
        try {
            String jsonParams = om.writeValueAsString(parameters);
            return om.readValue(jsonParams, ConstructionParameters.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void setParameters(ConstructionParameters parameters) {
        try {
            String jsonParams = om.writeValueAsString(parameters);
            this.parameters = om.readValue(jsonParams, ConstructionParameters.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
