package com.ksilisk.sapr.calculator.impl;

import com.ksilisk.sapr.calculator.SaprCalculationBiFunction;
import org.apache.commons.math3.util.Precision;

// Калькулятор нормальных напряжений (Ux)
public class NormalVoltageCalculation implements SaprCalculationBiFunction {
    private static final String FORMATTER = "U%dx: (%f * x^2) + (%f * x) + (%f)";
    private final double firstArg;
    private final double secondArg;
    private final double thirdArg;

    public NormalVoltageCalculation(double firstArg, double secondArg, double thirdArg) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
        this.thirdArg = thirdArg;
    }

    @Override
    public Double apply(Double x, Integer integer) {
        double result = (firstArg * Math.pow(x, 2)) + (secondArg * x) + thirdArg;
        return Precision.round(result, integer);
    }

    @Override
    public String stringRepresentation(int index) {
        return String.format(FORMATTER, index, firstArg, secondArg, thirdArg);
    }

    @Override
    public String toString() {
        return "NormalVoltageCalculation{" +
                "firstArg=" + firstArg +
                ", secondArg=" + secondArg +
                ", thirdArg=" + thirdArg +
                '}';
    }
}
