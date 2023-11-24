package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.calculator.CalculatorResult;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PostprocessorController implements Initializable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PostprocessorController.class);
    private static final List<Integer> PRECISIONS = List.of(0, 1, 2, 3, 4, 5, 6);

    @FXML
    private TableView<CalculatorResult> results;
    @FXML
    private TableColumn<CalculatorResult, Double> xValue, movement, normalVolt, longForce;
    @FXML
    private ComboBox<String> barIndexes;
    @FXML
    private ComboBox<Integer> precisions;
    @FXML
    private TextField shiftStep;
    @FXML
    private TextField x;

    public void drawGraph(MouseEvent event) {
        // TODO implement this
    }

    public void drawDiagram(MouseEvent event) {
        // TODO implement this
    }

    public void calculate(MouseEvent event) {

    }

    public void calculateForX(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        precisions.getItems().addAll(PRECISIONS);
    }

    public void save(MouseEvent event) {

    }

    public void upload(MouseEvent event) {

    }
}
