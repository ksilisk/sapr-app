package com.ksilisk.sapr.service.impl;

import com.ksilisk.sapr.calculator.Calculator;
import com.ksilisk.sapr.calculator.CalculatorResult;
import com.ksilisk.sapr.service.DiagramCreator;
import javafx.scene.Group;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.math3.util.Precision;

// TODO refactor this
public class DiagramCreatorImpl implements DiagramCreator {
    @Override
    public Group create(Calculator calculator, int precision, double samplingStep, int stepPrecision, double[] barLengths) {
        AreaChart<Number, Number> nxArea = new AreaChart<>(new NumberAxis(), new NumberAxis());
        AreaChart<Number, Number> OxArea = new AreaChart<>(new NumberAxis(), new NumberAxis());
        AreaChart<Number, Number> uxArea = new AreaChart<>(new NumberAxis(), new NumberAxis());
        double leftBorder = 0.0;
        for (int i = 0; i < barLengths.length; i++) {
            XYChart.Series<Number, Number> nxSeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> OxSeries = new XYChart.Series<>();
            XYChart.Series<Number, Number> uxSeries = new XYChart.Series<>();
            for (double x = 0.0; x <= barLengths[i]; x += samplingStep) {
                CalculatorResult result = calculator.calculate(x, precision, i);
                nxSeries.getData().add(new XYChart.Data<>(leftBorder + Precision.round(x, stepPrecision), result.getLongitudinalForce()));
                OxSeries.getData().add(new XYChart.Data<>(leftBorder + Precision.round(x, stepPrecision), result.getNormalVoltage()));
                uxSeries.getData().add(new XYChart.Data<>(leftBorder + Precision.round(x, stepPrecision), result.getMovement()));
            }
            leftBorder += barLengths[i];
            nxSeries.setName(String.valueOf(i + 1));
            OxSeries.setName(String.valueOf(i + 1));
            uxSeries.setName(String.valueOf(i + 1));
            nxArea.getData().add(nxSeries);
            OxArea.getData().add(OxSeries);
            uxArea.getData().add(uxSeries);
        }
        Tab nxTab = new Tab("Nx", nxArea);
        nxTab.setClosable(false);
        Tab uxTab = new Tab("Ux", OxArea);
        uxTab.setClosable(false);
        Tab oxTab = new Tab("∂x", uxArea);
        oxTab.setClosable(false);
        TabPane tabPane = new TabPane(nxTab, uxTab, oxTab);
        return new Group(tabPane);
    }
}
