package com.ksilisk.sapr.storage;

import com.ksilisk.sapr.calculator.Calculator;

public enum CalculatorStorage {
    INSTANCE;

    private Calculator calculator;

    public synchronized Calculator getCalculator() {
        return calculator;
    }

    public synchronized void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }
}
