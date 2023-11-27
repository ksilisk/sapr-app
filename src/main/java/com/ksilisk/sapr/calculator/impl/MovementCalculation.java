package com.ksilisk.sapr.calculator.impl;

import com.ksilisk.sapr.calculator.SaprCalculationBiFunction;
import org.apache.commons.math3.util.Precision;

// Калькулятор перемещений (σx)
public class MovementCalculation implements SaprCalculationBiFunction {
    private static final String FORMATTER = "σ%dx: (%f * x) + (%f)";
    private final double firstArg;
    private final double secondArg;

    public MovementCalculation(double firstArg, double secondArg) {
        this.firstArg = firstArg;
        this.secondArg = secondArg;
    }

    @Override
    public Double apply(Double x, Integer precision) {
        double result = (firstArg * x) + secondArg;
        return Precision.round(result, precision);
    }

    @Override
    public String stringRepresentation(int index) {
        return String.format(FORMATTER, index, firstArg, secondArg);
    }

    @Override
    public String toString() {
        return "MovementCalculation{" +
                "firstArg=" + firstArg +
                ", secondArg=" + secondArg +
                '}';
    }
}
