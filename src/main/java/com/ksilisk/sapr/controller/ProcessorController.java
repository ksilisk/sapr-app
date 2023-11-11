package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.calculator.Calculator;
import com.ksilisk.sapr.service.CalculatorStorage;
import com.ksilisk.sapr.service.ProcessorService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProcessorController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorController.class);
    private final ProcessorService processorService = ProcessorService.getInstance();
    private final CalculatorStorage calculatorStorage = CalculatorStorage.INSTANCE;

    @FXML
    private Label lable;

    public void click() {
        Calculator calculator = processorService.process();
        lable.setText(calculator.toString());
        calculatorStorage.setCalculator(calculator);
    }
}
