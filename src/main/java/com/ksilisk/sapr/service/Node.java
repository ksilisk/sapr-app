package com.ksilisk.sapr.service;

public class Node {
    private long XLoad;
    private long YLoad;

    public Node(long XLoad, long YLoad) {
        this.XLoad = XLoad;
        this.YLoad = YLoad;
    }

    public long getXLoad() {
        return XLoad;
    }

    public long getYLoad() {
        return YLoad;
    }
}
