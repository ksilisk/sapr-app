package com.ksilisk.sapr.payload;

import com.ksilisk.sapr.dto.BarDTO;

import java.util.ArrayList;
import java.util.List;

public record Construction(
        List<Bar> bars,
        List<Node> nodes,
        boolean leftSupport,
        boolean rightSupport) {

    public static Construction fromParameters(ConstructionParameters constructionParameters) {
        List<Bar> bars = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        for (int barInd = 0; barInd < constructionParameters.bars().size(); barInd++) {
            Bar newBar = new Bar();
            BarDTO oldBar = constructionParameters.bars().get(barInd);
            newBar.setArea(oldBar.getArea());
            newBar.setLength(oldBar.getLength());
            newBar.setElasticMod(constructionParameters.barSpecs().get(oldBar.getSpecInd() - 1).getElasticMod());
            newBar.setPermisVolt(constructionParameters.barSpecs().get(oldBar.getSpecInd() - 1).getPermisVolt());
            newBar.setXLoad(constructionParameters.barLoads().get(barInd).getBarQx());
            bars.add(newBar);
        }
        constructionParameters.nodeLoads().forEach(node -> {
            Node newNode = new Node();
            newNode.setXLoad(node.getNodeFx());
            nodes.add(newNode);
        });
        return new Construction(bars, nodes, constructionParameters.leftSupport(), constructionParameters.rightSupport());
    }
}
