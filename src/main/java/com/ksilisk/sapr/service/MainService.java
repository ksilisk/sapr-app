package com.ksilisk.sapr.service;

public class MainService {
    private static MainService INSTANCE;

    private final ConstructionStorage constructionStorage = ConstructionStorage.INSTANCE;
    private final CalculatorStorage calculatorStorage = CalculatorStorage.INSTANCE;

    public boolean isConstructionNotPresent() {
        return constructionStorage.getParameters() == null;
    }

    public boolean isCalculatorNotPresent() {
        return calculatorStorage.getCalculator() == null;
    }

    public static MainService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainService();
        }
        return INSTANCE;
    }
}
