package com.ksilisk.sapr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BarLoadDTO {
    @JsonProperty("bar_index")
    private int barInd;
    @JsonProperty("qx")
    private double barQx;

    public BarLoadDTO() {
        this(1, 0);
    }

    public BarLoadDTO(int barInd, double barQx) {
        this.barInd = barInd;
        this.barQx = barQx;
    }

    public int getBarInd() {
        return barInd;
    }

    public void setBarInd(int barInd) {
        this.barInd = barInd;
    }

    public double getBarQx() {
        return barQx;
    }

    public void setBarQx(double barQx) {
        this.barQx = barQx;
    }

    @Override
    public String toString() {
        return "BarLoadDTO{" +
                "barInd=" + barInd +
                ", barQx=" + barQx +
                '}';
    }
}
