package com.ksilisk.sapr.record;

import javafx.beans.property.SimpleDoubleProperty;

public class XCRecord {

    private double EValue;
    private double BValue;

    public XCRecord(double EValue, double BValue) {
        this.EValue = EValue;
        this.BValue = BValue;
    }

    public double getEValue() {
        return EValue;
    }

    public void setEValue(double EValue) {
        this.EValue = EValue;
    }

    public double getBValue() {
        return BValue;
    }

    public void setBValue(double BValue) {
        this.BValue = BValue;
    }
}
