package com.ksilisk.sapr.service;

import com.ksilisk.sapr.payload.Bar;
import com.ksilisk.sapr.payload.Construction;
import com.ksilisk.sapr.payload.Node;
import org.apache.commons.math3.geometry.Vector;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.util.List;

public class ProcessorService {
    private static ProcessorService INSTANCE;

    public double calculate(Construction construction) {

        return 0;
    }

    private double generateDeltas(List<Bar> bars, List<Node> nodes, boolean left, boolean right) {
        int count = bars.size();
        double[][] A = generateReactionMatrix(bars, left, right);
        double[] B = generateReactionGlobalVector(bars, nodes, left, right);
        RealMatrix matrix = MatrixUtils.createRealMatrix(A);
        RealMatrix inverse = MatrixUtils.inverse(matrix);
        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        DecompositionSolver ds=svd.getSolver();
        ds.solve(matrix);
//        Vector<Euclidean2D> doubleVector = new Vector2D(A);
//        doubleVector.dotProduct()
        return 0;
    }

    private double[][] generateReactionMatrix(List<Bar> bars, boolean left, boolean right) {
        int count = bars.size();
        double[][] A = new double[count + 1][count + 1];
        for (int i = 0; i < bars.size(); i++) {
            Bar bar = bars.get(i);
            A[i][i] += bar.getArea() * bar.getElasticMod() / bar.getLength();
            A[i][i + 1] -= bar.getArea() * bar.getElasticMod() / bar.getLength();
            A[i + 1][i] -= bar.getArea() * bar.getElasticMod() / bar.getLength();
            A[i + 1][i + 1] += bar.getArea() * bar.getElasticMod() / bar.getLength();
        }
        if (left) {
            A[0][0] = 1;
            A[1][0] = 0;
            A[0][1] = 0;
        }
        if (right) {
            A[count][count] = 1;
            A[count - 1][count] = 0;
            A[count][count - 1] = 0;
        }
        return A;
    }

    private double[] generateReactionGlobalVector(List<Bar> bars, List<Node> nodes, boolean left, boolean right) {
        int count = bars.size();
        double[] knots = new double[count + 1];
        for (int i = 0; i < count; i++) {
            knots[i + 1] = nodes.get(i).getXLoad();
        }
        double[] B = new double[count + 1];
        for (int i = 0; i < count + 1; i++) {
            B[i] += knots[i];
            if (i != 0) {
                B[i] += bars.get(i - 1).getPermisVolt() * bars.get(i - 1).getLength() / 2;
            }
            if (i != count) {
                B[i] += bars.get(i).getPermisVolt() * bars.get(i).getLength() / 2;
            }
        }
        if (left) {
            B[0] = 0;
        }
        if (right) {
            B[count] = 0;
        }
        return B;
    }

    public static synchronized ProcessorService getInstance() {
        if (INSTANCE == null) {
            return new ProcessorService();
        }
        return INSTANCE;
    }
}
