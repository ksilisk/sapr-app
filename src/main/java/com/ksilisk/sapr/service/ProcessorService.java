package com.ksilisk.sapr.service;

import com.ksilisk.sapr.calculator.Calculator;
import com.ksilisk.sapr.calculator.impl.LongitudinalForceCalculation;
import com.ksilisk.sapr.calculator.impl.MovementCalculation;
import com.ksilisk.sapr.calculator.impl.NormalVoltageCalculation;
import com.ksilisk.sapr.payload.Bar;
import com.ksilisk.sapr.payload.Construction;
import com.ksilisk.sapr.payload.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.List;
import java.util.Optional;

import static com.ksilisk.sapr.payload.Construction.fromParameters;
import static org.apache.commons.math3.linear.MatrixUtils.createRealMatrix;

public class ProcessorService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorService.class);
    private static ProcessorService INSTANCE;
    private final ConstructionStorage constructionStorage = ConstructionStorage.INSTANCE;
    private final CalculatorStorage calculatorStorage = CalculatorStorage.INSTANCE;

    public Optional<String> process() {
        try {
            Construction construction = fromParameters(constructionStorage.getParameters());
            Calculator calculator = calculate(construction);
            calculatorStorage.setCalculator(calculator);
            return Optional.of(calculator.getStringRepresentation());
        } catch (Exception e) {
            log.error("Error while process construction calculator. Construction parameters: {}", constructionStorage.getParameters(), e);
            new Alert(Alert.AlertType.ERROR,
                    "Internal Application Error. Try again or contact to me.", ButtonType.OK).show();
        }
        return Optional.empty();
    }

    private Calculator calculate(Construction construction) {
        log.info("Start calculating for construction. Construction: {}", construction);
        int nodeCount = construction.nodes().size();
        int barCount = nodeCount - 1;
        List<Double> elasticMods = construction.bars().stream().map(Bar::getElasticMod).toList();
        List<Double> areas = construction.bars().stream().map(Bar::getArea).toList();
        List<Double> lengths = construction.bars().stream().map(Bar::getLength).toList();
        double[] nodeLoads = construction.nodes().stream().mapToDouble(Node::getXLoad).toArray();
        double[] barLoads = construction.bars().stream().mapToDouble(Bar::getXLoad).toArray();
        double[][] reactionVectorData = new double[nodeCount][1];
        double[][] reactionMatrixData = new double[nodeCount][nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                if (i == j && i > 0 && i < barCount) {
                    reactionMatrixData[i][j] = (elasticMods.get(i - 1) * areas.get(i - 1)) / lengths.get(i - 1) + (elasticMods.get(j) * areas.get(j)) / lengths.get(j);
                } else if (i == j + 1) {
                    reactionMatrixData[i][j] = -(elasticMods.get(j) * areas.get(j)) / lengths.get(j);
                } else if (j == i + 1) {
                    reactionMatrixData[i][j] = -(elasticMods.get(i) * areas.get(i)) / lengths.get(i);
                } else {
                    reactionMatrixData[i][j] = .0;
                }
            }
        }
        for (int idx = 1; idx < barCount; idx++) {
            reactionVectorData[idx][0] = nodeLoads[idx] + barLoads[idx] * lengths.get(idx) / 2 + barLoads[idx - 1] * lengths.get(idx - 1) / 2;
        }
        if (construction.leftSupport()) {
            reactionMatrixData[0][0] = 1.0;
            reactionMatrixData[0][1] = 0.0;
            reactionMatrixData[1][0] = 0.0;
            reactionVectorData[0][0] = 0.0;
        } else {
            reactionMatrixData[0][0] = (elasticMods.get(0) * areas.get(0)) / lengths.get(0);
            reactionVectorData[0][0] = nodeLoads[0] + barLoads[0] * lengths.get(0) / 2;
        }
        if (construction.rightSupport()) {
            reactionMatrixData[barCount][barCount] = 1.0;
            reactionMatrixData[barCount - 1][barCount] = 0.0;
            reactionMatrixData[barCount][barCount - 1] = 0.0;
            reactionVectorData[barCount][0] = 0.0;
        } else {
            reactionMatrixData[barCount][barCount] = (elasticMods.get(barCount - 1) * areas.get(barCount - 1)) / lengths.get(barCount - 1);
            reactionVectorData[barCount][0] = nodeLoads[barCount] + barLoads[barCount - 1] * lengths.get(barCount - 1) / 2;
        }
        double[] uZeros = new double[barCount];
        double[] uLengths = new double[barCount];
        RealMatrix deltaVector = createDeltaMatrix(reactionMatrixData, reactionVectorData);
        for (int idx = 0; idx < barCount; idx++) {
            uZeros[idx] = deltaVector.getEntry(idx, 0);
        }
        System.arraycopy(uZeros, 1, uLengths, 0, barCount - 1);
        uLengths[barCount - 1] = deltaVector.getEntry(barCount, 0);
        Calculator.CalculatorBuilder calculatorBuilder = Calculator.builder();
        for (int idx = 0; idx < barCount; idx++) {
            double elasticity = elasticMods.get(idx);
            double area = areas.get(idx);
            double length = lengths.get(idx);
            double nxb = calculateNxb(elasticity, area, length, uZeros[idx], uLengths[idx], barLoads[idx]);
            double uxa = calculateUxa(elasticity, area, barLoads[idx]);
            double uxb = calculateUxb(elasticity, area, length, uZeros[idx], uLengths[idx], barLoads[idx]);
            calculatorBuilder.addMovementCalculation(new MovementCalculation(-barLoads[idx] / areas.get(idx), nxb / areas.get(idx)));
            calculatorBuilder.addNormalVoltageCalculation(new NormalVoltageCalculation(uxa, uxb, uZeros[idx]));
            calculatorBuilder.addLongitudinalForcesCalculation(new LongitudinalForceCalculation(-barLoads[idx], nxb));
        }
        return calculatorBuilder.build();
    }

    private RealMatrix createDeltaMatrix(double[][] reactionMatrixData, double[][] reactionVectorData) {
        RealMatrix reactionMatrix = createRealMatrix(reactionMatrixData);
        RealMatrix reactionVector = createRealMatrix(reactionVectorData);
        RealMatrix inverseReactionMatrix = new LUDecomposition(reactionMatrix).getSolver().getInverse();
        return inverseReactionMatrix.multiply(reactionVector);
    }

    private double calculateNxb(double elasticMod, double area, double length, double Up0, double UpL, double q) {
        return (elasticMod * area / length) * (UpL - Up0) + q * length / 2;
    }

    private double calculateUxb(double E, double A, double L, double Up0, double UpL, double q) {
        return (UpL - Up0 + (q * L * L) / (2 * E * A)) / L;
    }

    private double calculateUxa(double E, double A, double q) {
        return -q / (2 * E * A);
    }

    public static synchronized ProcessorService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProcessorService();
        }
        return INSTANCE;
    }
}
