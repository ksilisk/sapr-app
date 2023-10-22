package com.ksilisk.sapr.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BarLoadDTO {
    @JsonProperty("bar_index")
    private int barInd;
    @JsonProperty("qx")
    private double barQx;
    @JsonProperty("qy")
    private double barQy;

    public BarLoadDTO() {
        this(1, 0, 0);
    }

    public BarLoadDTO(int barInd, double barQx, double barQy) {
        this.barInd = barInd;
        this.barQx = barQx;
        this.barQy = barQy;
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

    public double getBarQy() {
        return barQy;
    }

    public void setBarQy(double barQy) {
        this.barQy = barQy;
    }
}
