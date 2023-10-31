package com.ksilisk.sapr.service;

import com.ksilisk.sapr.payload.ConstructionParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class ConstructionStorageTest {
    private final ConstructionStorage storage = ConstructionStorage.INSTANCE;

    @BeforeEach
    void setUp() {
        storage.setParameters(null);
    }

    @Test
    void getParametersTest_getNullParam() {
        Assertions.assertNull(storage.getParameters());
    }

    @Test
    void getParametersTest_getExistsParam_refShouldNotBeSame() {
        // given
        ConstructionParameters parameters = new ConstructionParameters(Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), false, false);
        // when
        storage.setParameters(parameters);
        // then
        Assertions.assertNotSame(parameters, storage.getParameters());
    }
}