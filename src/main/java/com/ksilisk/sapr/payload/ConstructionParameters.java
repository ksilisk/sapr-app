package com.ksilisk.sapr.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;

import java.util.List;

import static com.ksilisk.sapr.utils.CollectionsUtils.equalLists;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstructionParameters that = (ConstructionParameters) o;
        if (leftSupport != that.leftSupport) return false;
        if (rightSupport != that.rightSupport) return false;
        if (!equalLists(that.bars, bars)) return false;
        if (!equalLists(that.barLoads, barLoads)) return false;
        if (!equalLists(that.barSpecs, barSpecs)) return false;
        return equalLists(that.nodeLoads, nodeLoads);
    }
}
