package com.ksilisk.sapr.validate;

import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;

import java.util.List;

public interface Validator {
    boolean validate(List<BarDTO> bars, List<BarLoadDTO> barLoads, List<BarSpecDTO> barSpecs, List<NodeLoadDTO> nodeLoads);
}
