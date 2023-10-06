package com.ksilisk.sapr.record;

public class QSRecord {
    private int NValue;
    private double QXValue;

    public QSRecord(int NValue, double QXValue) {
        this.NValue = NValue;
        this.QXValue = QXValue;
    }

    public int getNValue() {
        return NValue;
    }

    public void setNValue(int NValue) {
        this.NValue = NValue;
    }

    public double getQXValue() {
        return QXValue;
    }

    public void setQXValue(double QXValue) {
        this.QXValue = QXValue;
    }
}
