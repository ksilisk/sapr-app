package com.ksilisk.sapr.service;

import com.ksilisk.sapr.calculator.Calculator;
import javafx.scene.Group;

public interface GraphCreator {
    // TODO refactor this shit
    Group create(Calculator calculator, int barIndex, int precision, double samplingStep, double barLength, int stepPrecision);
}
