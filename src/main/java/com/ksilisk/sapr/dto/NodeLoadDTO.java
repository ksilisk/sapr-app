package com.ksilisk.sapr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeLoadDTO {
    @JsonProperty("node_index")
    private int nodeInd;
    @JsonProperty("fx")
    private double nodeFx;
    @JsonProperty("fy")
    private double nodeFy;

    public NodeLoadDTO() {
        this(1, 0);
    }

    public NodeLoadDTO(int nodeInd, double nodeFx) {
        this.nodeInd = nodeInd;
        this.nodeFx = nodeFx;
    }

    public int getNodeInd() {
        return nodeInd;
    }

    public void setNodeInd(int nodeInd) {
        this.nodeInd = nodeInd;
    }

    public double getNodeFx() {
        return nodeFx;
    }

    public void setNodeFx(double nodeFx) {
        this.nodeFx = nodeFx;
    }
}
