package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.calculator.CalculatorResult;
import com.ksilisk.sapr.service.PostprocessorService;
import com.ksilisk.sapr.service.PostprocessorTableCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class PostprocessorController implements Initializable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PostprocessorController.class);
    private static final List<Integer> PRECISIONS = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    @FXML
    private TableView<CalculatorResult> resultsView;
    @FXML
    private TableColumn<CalculatorResult, Double> xValue, movement, normalVolt, longForce;
    @FXML
    private ComboBox<Integer> barIndexes;
    @FXML
    private ComboBox<Integer> precisions;
    @FXML
    private TextField samplingStep, x;

    private final PostprocessorService service = PostprocessorService.getInstance();

    private volatile boolean needClearResults = false;

    public void calculate() {
        int barIndex = barIndexes.getValue();
        int precision = precisions.getValue();
        String step = samplingStep.getText();
        service.calculate(barIndex, step, precision)
                .ifPresent(calculatorResults -> {
                    resultsView.getItems().clear();
                    resultsView.getItems().addAll(calculatorResults);
                    needClearResults = true;
                });
    }

    public void calculateForX() {
        int precision = precisions.getValue();
        String xVal = x.getText();
        service.calculateForX(xVal, precision)
                .ifPresent(calculatorResult -> {
                    if (needClearResults) {
                        resultsView.getItems().clear();
                        needClearResults = false;
                    }
                    resultsView.getItems().add(calculatorResult);
                });
    }

    public void drawGraph() {
        int barIndex = barIndexes.getValue();
        int precision = precisions.getValue();
        String step = samplingStep.getText();
        service.drawGraph(step, barIndex, precision);
    }

    public void drawDiagram() {
        int precision = precisions.getValue();
        String step = samplingStep.getText();
        service.drawDiagram(step, precision);
    }

    public void save(MouseEvent event) {
        List<CalculatorResult> calculatorResults = resultsView.getItems();
        service.save(calculatorResults, ((Button) event.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("Start initialize postprocessor controller");
        precisions.getItems().addAll(PRECISIONS);
        IntStream.rangeClosed(1, service.getCountBars()).forEach(ind -> barIndexes.getItems().add(ind));
        precisions.setValue(precisions.getItems().get(0));
        barIndexes.setValue(barIndexes.getItems().get(0));
        movement.setCellFactory(col ->
                new PostprocessorTableCell<>(service.getPermisVolts(), () -> barIndexes.getValue() - 1));
        initColumns();
    }

    private void initColumns() {
        xValue.setCellValueFactory(new PropertyValueFactory<>("x"));
        longForce.setCellValueFactory(new PropertyValueFactory<>("longitudinalForce"));
        movement.setCellValueFactory(new PropertyValueFactory<>("movement"));
        normalVolt.setCellValueFactory(new PropertyValueFactory<>("normalVoltage"));
    }
}
