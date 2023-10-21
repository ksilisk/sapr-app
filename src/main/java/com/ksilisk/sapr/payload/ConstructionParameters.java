package com.ksilisk.sapr.payload;

import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;

import java.util.List;

public record ConstructionParameters(List<BarDTO> bars, List<BarLoadDTO> barLoads, List<BarSpecDTO> barSpecs,
                                     List<NodeLoadDTO> nodeLoads, boolean leftSupport, boolean rightSupport) {
}
