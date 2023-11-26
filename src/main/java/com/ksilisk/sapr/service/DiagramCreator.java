package com.ksilisk.sapr.service;

import com.ksilisk.sapr.calculator.Calculator;
import javafx.scene.Group;

public interface DiagramCreator {
    // TODO refactor this shit
    Group create(Calculator calculator, int precision, double samplingStep, int stepPrecision, double[] barLengths);
}
