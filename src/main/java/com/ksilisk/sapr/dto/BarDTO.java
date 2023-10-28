package com.ksilisk.sapr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BarDTO {
    @JsonProperty("length")
    private double length;
    @JsonProperty("area")
    private double area;
    @JsonProperty("spec_index")
    private int specInd;

    public BarDTO() {
        this(0, 0, 1);
    }

    public BarDTO(double length, double area, int specInd) {
        this.length = length;
        this.area = area;
        this.specInd = specInd;
    }

    public int getSpecInd() {
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

    @Override
    public String toString() {
        return "BarDTO{" +
                "length=" + length +
                ", area=" + area +
                ", specInd=" + specInd +
                '}';
    }
}
