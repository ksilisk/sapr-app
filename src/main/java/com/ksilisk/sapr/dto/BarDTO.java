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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BarDTO barDTO = (BarDTO) obj;
        if (barDTO.area != area) return false;
        if (barDTO.specInd != specInd) return false;
        return barDTO.length == length;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(length);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(area);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + specInd;
        return result;
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
