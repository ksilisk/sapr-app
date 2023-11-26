package com.ksilisk.sapr.service.impl;

import com.ksilisk.sapr.calculator.Calculator;
import com.ksilisk.sapr.calculator.CalculatorResult;
import com.ksilisk.sapr.service.GraphCreator;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.math3.util.Precision;

import java.util.List;

// TODO refactor this
public class GraphCreatorImpl implements GraphCreator {
    private static final List<String> LOADS_NAME = List.of("Nx", "Ux", "∂x");

    @Override
    public Group create(Calculator calculator, int barIndex, int precision, double samplingStep, double barLength, int stepPrecision) {
//        ComboBox<String> comboBox = new ComboBox<>();
//        comboBox.getItems().addAll(LOADS_NAME);
//        comboBox.setValue(LOADS_NAME.get(0));
        XYChart.Series<Number, Number> nxSeries = new XYChart.Series<>();
        LineChart<Number, Number> nxChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        XYChart.Series<Number, Number> uxSeries = new XYChart.Series<>();
        LineChart<Number, Number> uxChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        XYChart.Series<Number, Number> oxSeries = new XYChart.Series<>(); // sigma
        LineChart<Number, Number> oxChart = new LineChart<>(new NumberAxis(), new NumberAxis()); // sigma
        for (double x = 0.0; x <= barLength; x += samplingStep) {
            CalculatorResult result = calculator.calculate(x, precision, barIndex);
            nxSeries.getData().add(new XYChart.Data<>(Precision.round(x, stepPrecision), result.getLongitudinalForce()));
            uxSeries.getData().add(new XYChart.Data<>(Precision.round(x, stepPrecision), result.getMovement()));
            oxSeries.getData().add(new XYChart.Data<>(Precision.round(x, stepPrecision), result.getNormalVoltage()));
        }
        nxSeries.setName(String.valueOf(barIndex + 1));
        uxSeries.setName(String.valueOf(barIndex + 1));
        oxSeries.setName(String.valueOf(barIndex + 1));
        nxChart.getData().add(nxSeries);
        uxChart.getData().add(uxSeries);
        oxChart.getData().add(oxSeries);
        Tab nxTab = new Tab("Nx", nxChart);
        Tab uxTab = new Tab("Ux", uxChart);
        Tab oxTab = new Tab("∂x", oxChart);
        TabPane pane = new TabPane(nxTab, uxTab, oxTab);
        return new Group(pane);
    }
}
