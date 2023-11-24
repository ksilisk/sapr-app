package com.ksilisk.sapr.calculator.impl;

import com.ksilisk.sapr.calculator.SaprCalculationBiFunction;
import org.apache.commons.math3.util.Precision;

// Калькулятор нормальных напряжений (sigmaX)
public class NormalVoltageCalculation implements SaprCalculationBiFunction {
    private static final String FORMATTER = "∂%dx: (%f * x^2) + (%f * x) + (%f)";
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
    public String toString(int index) {
        return String.format(FORMATTER, index, firstArg, secondArg, thirdArg);
    }
}
