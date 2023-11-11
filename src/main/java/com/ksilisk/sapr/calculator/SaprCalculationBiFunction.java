package com.ksilisk.sapr.calculator;

import java.util.function.BiFunction;

public interface SaprCalculationBiFunction extends BiFunction<Double, Integer, Double> {
    String toString(int index);
}
