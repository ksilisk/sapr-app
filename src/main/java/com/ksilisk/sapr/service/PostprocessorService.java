package com.ksilisk.sapr.service;

import com.ksilisk.sapr.calculator.Calculator;
import com.ksilisk.sapr.calculator.CalculatorResult;
import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.validate.ValidationException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.math3.util.Precision;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import static com.ksilisk.sapr.config.SaprBarConfig.USER_HOME_DIRECTORY;

public class PostprocessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PostprocessorService.class);
    private static final List<String> RESULT_FILE_EXTENSIONS = List.of("*.csv");
    private static PostprocessorService INSTANCE;

    private final CalculatorStorage calculatorStorage = CalculatorStorage.INSTANCE;
    private final ConstructionStorage constructionStorage = ConstructionStorage.INSTANCE;
    private final FileChooser fileChooser = new FileChooser();

    public PostprocessorService() {
        fileChooser.setInitialDirectory(USER_HOME_DIRECTORY);
        fileChooser.setTitle("Choose file");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Result file extensions", RESULT_FILE_EXTENSIONS));
    }

    public Optional<List<CalculatorResult>> calculate(int barIndex, String shiftStep, int precision) {
        try {
            double parsedStep = tryParseDouble(shiftStep);
            if (parsedStep == 0.0) {
                throw new ValidationException("Sampling step value can't be null");
            }
            int stepPrecision = getNumberPrecision(shiftStep);
            Calculator calculator = calculatorStorage.getCalculator();
            double barLength = constructionStorage.getParameters().bars().get(barIndex - 1).getLength();
            List<CalculatorResult> calculatorResults = new ArrayList<>();
            for (double x = 0.0; x <= barLength; x += parsedStep) {
                calculatorResults.add(calculator.calculate(Precision.round(x, stepPrecision), precision, barIndex - 1));
            }
            return Optional.of(calculatorResults);
        } catch (ValidationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (Exception e) {
            log.error("Error while calculating result for bar. Bar index: {}, ShiftStep: {}, Precision: {}",
                    barIndex, shiftStep, precision, e);
            new Alert(Alert.AlertType.ERROR,
                    "Internal Application Error. Try again or contact to me.", ButtonType.OK).show();
        }
        return Optional.empty();
    }

    public Optional<CalculatorResult> calculateForX(String x, int precision) {
        try {
            double parsedX = tryParseDouble(x);
            Calculator calculator = calculatorStorage.getCalculator();
            double[] barLengths = constructionStorage.getParameters().bars().stream().mapToDouble(BarDTO::getLength).toArray();
            double start = 0.0;
            for (int barInd = 0; barInd < barLengths.length; barInd++) {
                start += barLengths[barInd];
                if (parsedX <= start && parsedX >= 0.0) {
                    return Optional.of(calculator.calculate(parsedX, precision, barInd));
                }
            }
            throw new ValidationException("X value out of range");
        } catch (ValidationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (Exception e) {
            log.error("Error while calculating result for custom X value. X value: {}, Precision: {}", x, precision, e);
            new Alert(Alert.AlertType.ERROR,
                    "Internal Application Error. Try again or contact to me.", ButtonType.OK).show();
        }
        return Optional.empty();
    }

    public void save(List<CalculatorResult> calculatorResults, Window window) {
        try {
            log.info("Start saving calculations result to file. Results: {}", calculatorResults);
            File chosenFile = fileChooser.showSaveDialog(window);
            if (chosenFile == null) {
                return;
            }
            try (BufferedWriter writer = Files.newBufferedWriter(chosenFile.toPath())) {
                writer.write(prepareResultsForSaving(calculatorResults));
            }
        } catch (Exception e) {
            log.error("Error while save result constriction calculating. Results: {}", calculatorResults, e);
            new Alert(Alert.AlertType.ERROR,
                    "Internal Application Error. Try again or contact to me.", ButtonType.OK).show();
        }
    }

    private String prepareResultsForSaving(List<CalculatorResult> results) {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("x;Nx;Ux;∂x");
        for (CalculatorResult result : results) {
            String resultLine = result.getX() + ";" + result.getLongitudinalForce() + ";" + result.getMovement() + ";" + result.getNormalVoltage();
            joiner.add(resultLine);
        }
        return joiner.toString();
    }

    public int getCountBars() {
        return constructionStorage.getParameters().bars().size();
    }

    private double tryParseDouble(String number) {
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            throw new ValidationException("Incorrect value at TextBox");
        }
    }

    private int getNumberPrecision(String number) {
        String[] dotSplit = number.split("\\.");
        if (dotSplit.length == 1) {
            return 0;
        }
        return dotSplit[1].length();
    }

    public static synchronized PostprocessorService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PostprocessorService();
        }
        return INSTANCE;
    }
}
