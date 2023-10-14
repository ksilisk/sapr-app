package com.ksilisk.sapr.service;

public class Bar {
    private long length;
    private long XLoad;
    private long YLoad;
    private long area;

    public Bar(long length, long XLoad, long YLoad, long area) {
        this.length = length;
        this.XLoad = XLoad;
        this.YLoad = YLoad;
        this.area = area;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getXLoad() {
        return XLoad;
    }

    public void setXLoad(long XLoad) {
        this.XLoad = XLoad;
    }

    public long getYLoad() {
        return YLoad;
    }

    public void setYLoad(long YLoad) {
        this.YLoad = YLoad;
    }

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }
}
