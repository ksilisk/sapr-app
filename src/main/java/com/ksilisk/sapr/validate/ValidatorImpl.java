package com.ksilisk.sapr.validate;

import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;

import java.util.Comparator;
import java.util.List;

public class ValidatorImpl implements Validator {
    @Override
    public boolean validate(List<BarDTO> bars, List<BarLoadDTO> barLoads, List<BarSpecDTO> barSpecs, List<NodeLoadDTO> nodeLoads) {
        if (nodeLoads.size() - bars.size() != 1) {
            return false;
        }
        if (barLoads.size() != bars.size()) {
            return false;
        }
        return bars.stream()
                .map(BarDTO::getSpecInd)
                .max(Comparator.naturalOrder())
                .filter(integer -> (barSpecs.size() - 1) >= integer)
                .isPresent();
    }
}
