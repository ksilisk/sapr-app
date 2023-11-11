package com.ksilisk.sapr.calculator.impl;

import com.ksilisk.sapr.calculator.SaprCalculationBiFunction;
import org.apache.commons.math3.util.Precision;

// Калькулятор продольных сил (Nx)
public class LongitudinalForceCalculation implements SaprCalculationBiFunction {
    private static final String FORMATTER = "N%dx: (%f * x) + (%f)";

    private final double firstArg;
    private final double secondArg;

    public LongitudinalForceCalculation(double firstArg, double secondArg) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
    }

    @Override
    public Double apply(Double x, Integer precision) {
        double result = (firstArg * x) + secondArg;
        return Precision.round(result, precision);
    }

    @Override
    public String toString(int index) {
        return String.format(FORMATTER, index, firstArg, secondArg);
    }
}
