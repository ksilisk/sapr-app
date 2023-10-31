package com.ksilisk.sapr.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;

import java.util.List;

public record ConstructionParameters(
        @JsonProperty("bars")
        List<BarDTO> bars,
        @JsonProperty("bar_loads")
        List<BarLoadDTO> barLoads,
        @JsonProperty("bar_specs")
        List<BarSpecDTO> barSpecs,
        @JsonProperty("node_loads")
        List<NodeLoadDTO> nodeLoads,
        @JsonProperty("left_support")
        boolean leftSupport,
        @JsonProperty("right_support")
        boolean rightSupport) {
}
