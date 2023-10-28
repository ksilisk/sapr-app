package com.ksilisk.sapr.validate;

import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.payload.ConstructionParameters;

import java.util.List;

public class ValidatorImpl implements Validator {
    @Override
    public void validate(ConstructionParameters constructionParameters) {
        checkSpecs(constructionParameters.bars(), constructionParameters.barSpecs());
        checkBars(constructionParameters.bars());
        checkSizes(constructionParameters);
    }

    private void checkBars(List<BarDTO> bars) {
        if (!bars.stream().allMatch(bar -> bar.getArea() > 0 && bar.getLength() > 0)) {
            throw new ValidationException("Параметры стержней заданы неверно");
        }
    }

    private void checkSizes(ConstructionParameters constructionParameters) {
        List<Integer> countBarLoads = constructionParameters.barLoads().stream()
                .map(BarLoadDTO::getBarInd)
                .sorted()
                .toList();
        List<Integer> countNodeLoads = constructionParameters.nodeLoads().stream()
                .map(NodeLoadDTO::getNodeInd)
                .sorted()
                .toList();
        for (int i = 0; i < countBarLoads.size(); i++) {
            if (countBarLoads.get(i) != i + 1) {
                throw new ValidationException("Не заданы нагрузки на все стержни конструкции");
            }
        }
        for (int i = 0; i < countNodeLoads.size(); i++) {
            if (countNodeLoads.get(i) != i + 1) {
                throw new ValidationException("Не заданы нагрузки на все узлы конструкции");
            }
        }
        if (countNodeLoads.size() - constructionParameters.bars().size() != 1) {
            throw new ValidationException("Не заданы нагрузки на все узлы конструкции");
        }
        if (countBarLoads.size() != constructionParameters.bars().size()) {
            throw new ValidationException("Не заданы нагрузки на все стержни конструкции");
        }
        if (!(constructionParameters.leftSupport() || constructionParameters.rightSupport())) {
            throw new ValidationException("У конструкции должна быть как минимум одна опора");
        }
    }

    private void checkSpecs(List<BarDTO> bars, List<BarSpecDTO> barSpecs) {
        if (!bars.stream().allMatch(bar -> bar.getSpecInd() > 0 && bar.getSpecInd() <= barSpecs.size())) {
            throw new ValidationException("Заданы не все свойства стежней");
        }
        if (!barSpecs.stream().allMatch(spec -> spec.getElasticMod() > 0 && spec.getPermisVolt() > 0)) {
            throw new ValidationException("Значения свойств стержней заданы неверно");
        }
    }
}
