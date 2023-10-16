package com.ksilisk.sapr.dto;

public class NodeLoadDTO {
    private int nodeInd;
    private double nodeFx;
    private double nodeFy;

    public NodeLoadDTO() {
        this(0, 0, 0);
    }

    public NodeLoadDTO(int nodeInd, double nodeFx, double nodeFy) {
        this.nodeInd = nodeInd;
        this.nodeFx = nodeFx;
        this.nodeFy = nodeFy;
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

    public double getNodeFy() {
        return nodeFy;
    }

    public void setNodeFy(double nodeFy) {
        this.nodeFy = nodeFy;
    }
}
