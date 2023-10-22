package com.ksilisk.sapr.validate;

import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.payload.ConstructionParameters;

import java.util.List;

public class ValidatorImpl implements Validator {
    @Override
    public void validate(ConstructionParameters constructionParameters) {
        checkSpecs(constructionParameters.bars(), constructionParameters.barSpecs());
        checkSizes(constructionParameters);
    }

    private void checkSizes(ConstructionParameters constructionParameters) {
        if (constructionParameters.nodeLoads().size() - constructionParameters.bars().size() != 1) {
            throw new ValidationException("Не заданы нагрузки на все узлы конструкции");
        }
        if (constructionParameters.barLoads().size() != constructionParameters.bars().size()) {
            throw new ValidationException("Не заданы нагрузки на все стержни конструкции");
        }
    }

    private void checkSpecs(List<BarDTO> bars, List<BarSpecDTO> barSpecs) {
        if (bars.stream().allMatch(bar -> bar.getSpecInd() > 0 && bar.getSpecInd() <= barSpecs.size())) {
            return;
        }
        throw new ValidationException("Свойства стержней заданы неверно");
    }
}
