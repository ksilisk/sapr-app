package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.service.ProcessorService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ProcessorController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorController.class);
    private final ProcessorService processorService = ProcessorService.getInstance();

    @FXML
    private TextArea result;

    public void click() {
        processorService.process()
                .ifPresent(result::setText);
    }
}
