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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BarLoadDTO that = (BarLoadDTO) obj;
        if (that.barQx != barQx) return false;
        return that.barInd == barInd;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = barInd;
        temp = Double.doubleToLongBits(barQx);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BarLoadDTO{" +
                "barInd=" + barInd +
                ", barQx=" + barQx +
                '}';
    }
}
