package com.ksilisk.sapr.payload;

public class Bar {
    double length;
    double XLoad;
    double YLoad;
    double area;
    double elasticMod;
    double permisVolt;

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

    public double getYLoad() {
        return YLoad;
    }

    public void setYLoad(double YLoad) {
        this.YLoad = YLoad;
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
