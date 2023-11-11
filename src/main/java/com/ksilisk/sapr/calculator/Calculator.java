package com.ksilisk.sapr.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Calculator {
    private final List<SaprCalculationBiFunction> movementsCalculations;
    private final List<SaprCalculationBiFunction> normalVoltageCalculations;
    private final List<SaprCalculationBiFunction> longitudinalForceCalculations;

    public Calculator(List<SaprCalculationBiFunction> movementsCalculations,
                      List<SaprCalculationBiFunction> normalVoltageCalculations,
                      List<SaprCalculationBiFunction> longitudinalForceCalculations) {
        this.movementsCalculations = movementsCalculations;
        this.normalVoltageCalculations = normalVoltageCalculations;
        this.longitudinalForceCalculations = longitudinalForceCalculations;
    }

    public CalculatorResult calculate(double x, int precision, int barIndex) {
        return new CalculatorResult(x, movementsCalculations.get(barIndex).apply(x, precision),
                longitudinalForceCalculations.get(barIndex).apply(x, precision),
                normalVoltageCalculations.get(barIndex).apply(x, precision));
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n", "Результаты расчета формул для конструкции:\n\n", "");
        for (int barIndex = 0; barIndex < movementsCalculations.size(); barIndex++) {
            joiner.add("Стержень №" + (barIndex + 1));
            joiner.add(movementsCalculations.get(barIndex).toString(barIndex + 1));
            joiner.add(normalVoltageCalculations.get(barIndex).toString(barIndex + 1));
            joiner.add(longitudinalForceCalculations.get(barIndex).toString(barIndex + 1));
            joiner.add("");
        }
        return joiner.toString();
    }

    public static CalculatorBuilder builder() {
        return new CalculatorBuilder();
    }

    public static class CalculatorBuilder {
        private final List<SaprCalculationBiFunction> movementsCalculations = new ArrayList<>();
        private final List<SaprCalculationBiFunction> normalVoltageCalculations = new ArrayList<>();
        private final List<SaprCalculationBiFunction> longitudinalForceCalculations = new ArrayList<>();

        public void addMovementCalculation(SaprCalculationBiFunction movementCalculation) {
            movementsCalculations.add(movementCalculation);
        }

        public void addNormalVoltageCalculation(SaprCalculationBiFunction normalVoltageCalculation) {
            normalVoltageCalculations.add(normalVoltageCalculation);
        }

        public void addLongitudinalForcesCalculation(SaprCalculationBiFunction longitudinalForcesCalculation) {
            longitudinalForceCalculations.add(longitudinalForcesCalculation);
        }

        public Calculator build() {
            if (movementsCalculations.size() == 0) {
                throw new IllegalStateException("Movements calculations can't be empty");
            }
            if (normalVoltageCalculations.size() == 0) {
                throw new IllegalStateException("Normal Voltage calculations can't be empty");
            }
            if (longitudinalForceCalculations.size() == 0) {
                throw new IllegalStateException("Longitudinal Force calculation can't be empty");
            }
            if (longitudinalForceCalculations.size() != movementsCalculations.size() ||
                    movementsCalculations.size() != normalVoltageCalculations.size()) {
                throw new IllegalStateException("Calculations size not equals");
            }
            return new Calculator(movementsCalculations, normalVoltageCalculations, longitudinalForceCalculations);
        }
    }
}
