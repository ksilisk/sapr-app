package com.ksilisk.sapr.payload;

public class Bar {
    private double length;
    private double XLoad;
    private double area;
    private double elasticMod;
    private double permisVolt;

    public Bar() {
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getXLoad() {
        return XLoad;
    }

    public void setXLoad(double XLoad) {
        this.XLoad = XLoad;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getElasticMod() {
        return elasticMod;
    }

    public void setElasticMod(double elasticMod) {
        this.elasticMod = elasticMod;
    }

    public double getPermisVolt() {
        return permisVolt;
    }

    public void setPermisVolt(double permisVolt) {
        this.permisVolt = permisVolt;
    }
}
