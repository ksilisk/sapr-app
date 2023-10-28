package com.ksilisk.sapr.validate;

import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.payload.ConstructionParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class ValidatorImplTest {
    private final Validator validator = new ValidatorImpl();

    @Test
    void validateEmptyData_shouldThrowException() {
        // given
        ConstructionParameters constructionParameters = new ConstructionParameters(emptyList(), emptyList(),
                emptyList(), emptyList(), true, true);
        // then
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(constructionParameters));
    }

    @Test
    void validateValidData_shouldNotThrowException() {
        // given
        ConstructionParameters constructionParameters = new ConstructionParameters(singletonList(new BarDTO(1, 1, 1)),
                singletonList(new BarLoadDTO(1, 0)), singletonList(new BarSpecDTO(1, 1)),
                Arrays.asList(new NodeLoadDTO(1, 1), new NodeLoadDTO(2, 1)),
                true, true);
        // then
        Assertions.assertDoesNotThrow(() -> validator.validate(constructionParameters));
    }

    @Test
    void noOneSupportValidateTest_shouldThrowException() {
        // given
        ConstructionParameters constructionParameters = new ConstructionParameters(singletonList(new BarDTO(1, 1, 1)),
                singletonList(new BarLoadDTO(1, 0)), singletonList(new BarSpecDTO(1, 1)),
                Arrays.asList(new NodeLoadDTO(1, 1), new NodeLoadDTO(2, 1)),
                false, false);
        // then
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(constructionParameters));
    }

    @Test
    void invalidNodeIndInLoadsTest_shouldThrowException() {
        // given
        ConstructionParameters constructionParameters = new ConstructionParameters(singletonList(new BarDTO(1, 1, 1)),
                singletonList(new BarLoadDTO(1, 0)), singletonList(new BarSpecDTO(1, 1)),
                Arrays.asList(new NodeLoadDTO(1, 1), new NodeLoadDTO(1, 1)),
                true, true);
        // then
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(constructionParameters));
    }

    @Test
    void invalidBarSpecsTest_shouldThrowException() {
        // given
        ConstructionParameters constructionParameters = new ConstructionParameters(singletonList(new BarDTO(1, 1, 1)),
                singletonList(new BarLoadDTO(1, 0)), singletonList(new BarSpecDTO(-11, -11)),
                Arrays.asList(new NodeLoadDTO(1, 1), new NodeLoadDTO(2, 1)),
                true, true);
        // then
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(constructionParameters));
    }

    @Test
    void invalidBarsParamsTest_shouldThrowException() {
        // given
        ConstructionParameters constructionParameters = new ConstructionParameters(singletonList(new BarDTO(-1, -1, 1)),
                singletonList(new BarLoadDTO(1, 0)), singletonList(new BarSpecDTO(1, 1)),
                Arrays.asList(new NodeLoadDTO(1, 1), new NodeLoadDTO(2, 1)),
                true, true);
        // then
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(constructionParameters));
    }
}