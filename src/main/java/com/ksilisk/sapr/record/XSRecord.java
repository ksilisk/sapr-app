package com.ksilisk.sapr.record;

public class XSRecord {
    private double LValue;
    private double AValue;
    private double XCValue;

    public XSRecord(double LValue, double AValue, double XCValue) {
        this.LValue = LValue;
        this.AValue = AValue;
        this.XCValue = XCValue;
    }

    public double getXCValue() {
        return XCValue;
    }

    public void setXCValue(double XCValue) {
        this.XCValue = XCValue;
    }

    public double getAValue() {
        return AValue;
    }

    public void setAValue(double AValue) {
        this.AValue = AValue;
    }

    public double getLValue() {
        return LValue;
    }

    public void setLValue(double LValue) {
        this.LValue = LValue;
    }
}
