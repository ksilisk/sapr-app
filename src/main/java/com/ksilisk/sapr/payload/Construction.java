package com.ksilisk.sapr.payload;

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
        constructionParameters.bars().forEach(bar -> {
            Bar newBar = new Bar();
            newBar.setArea(bar.getArea());
            newBar.setLength(bar.getLength());
            newBar.setXLoad(constructionParameters.barLoads().get(bar.getSpecInd() - 1).getBarQx());
            newBar.setElasticMod(constructionParameters.barSpecs().get(bar.getSpecInd() - 1).getElasticMod());
            newBar.setPermisVolt(constructionParameters.barSpecs().get(bar.getSpecInd() - 1).getPermisVolt());
            bars.add(newBar);
        });
        constructionParameters.nodeLoads().forEach(node -> {
            Node newNode = new Node();
            newNode.setXLoad(node.getNodeFx());
            nodes.add(newNode);
        });
        return new Construction(bars, nodes, constructionParameters.leftSupport(), constructionParameters.rightSupport());
    }
}
