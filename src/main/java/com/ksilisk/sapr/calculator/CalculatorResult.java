package com.ksilisk.sapr.calculator;

public class CalculatorResult {
    private double movement;
    private double x;
    private double longitudinalForce;
    private double normalVoltage;

    public CalculatorResult() {
        this(0, 0, 0, 0);
    }

    public CalculatorResult(double x, double movement, double longitudinalForce, double normalVoltage) {
        this.x = x;
        this.movement = movement;
        this.longitudinalForce = longitudinalForce;
        this.normalVoltage = normalVoltage;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getLongitudinalForce() {
        return longitudinalForce;
    }

    public void setLongitudinalForce(double longitudinalForce) {
        this.longitudinalForce = longitudinalForce;
    }

    public double getNormalVoltage() {
        return normalVoltage;
    }

    public void setNormalVoltage(double normalVoltage) {
        this.normalVoltage = normalVoltage;
    }

    public double getMovement() {
        return movement;
    }

    public void setMovement(double movement) {
        this.movement = movement;
    }

    @Override
    public String toString() {
        return "CalculatorResult{" +
                "movement=" + movement +
                ", x=" + x +
                ", longitudinalForce=" + longitudinalForce +
                ", normalVoltage=" + normalVoltage +
                '}';
    }
}
