package com.ksilisk.sapr.record;

public class QRRecord {
    private int NValue;
    private double FValue;

    public QRRecord(int NValue, double FValue) {
        this.NValue = NValue;
        this.FValue = FValue;
    }

    public int getNValue() {
        return NValue;
    }

    public void setNValue(int NValue) {
        this.NValue = NValue;
    }

    public double getFValue() {
        return FValue;
    }

    public void setFValue(double FValue) {
        this.FValue = FValue;
    }
}
