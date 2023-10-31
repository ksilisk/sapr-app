package com.ksilisk.sapr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeLoadDTO {
    @JsonProperty("node_index")
    private int nodeInd;
    @JsonProperty("fx")
    private double nodeFx;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NodeLoadDTO that = (NodeLoadDTO) obj;
        if (that.getNodeFx() != nodeFx) return false;
        return that.getNodeInd() == nodeInd;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nodeInd;
        temp = Double.doubleToLongBits(nodeFx);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "NodeLoadDTO{" +
                "nodeInd=" + nodeInd +
                ", nodeFx=" + nodeFx +
                '}';
    }
}
