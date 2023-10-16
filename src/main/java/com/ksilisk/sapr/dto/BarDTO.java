package com.ksilisk.sapr.dto;

public class BarDTO {
    private double length;
    private double area;
    private int specInd;

    public BarDTO() {
        this(0, 0, 0);
    }

    public BarDTO(double length, double area, int specInd) {
        this.length = length;
        this.area = area;
        this.specInd = specInd;
    }


    public double getSpecInd() {
        return specInd;
    }

    public void setSpecInd(int specInd) {
        this.specInd = specInd;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
