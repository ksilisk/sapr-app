package com.ksilisk.sapr.calculator;

import java.util.function.BiFunction;

public interface SaprCalculationBiFunction extends BiFunction<Double, Integer, Double> {
    String stringRepresentation(int index);
}
