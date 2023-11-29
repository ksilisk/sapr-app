package com.ksilisk.sapr.payload;

import javafx.scene.control.TableCell;

import java.util.function.Supplier;

public class PostprocessorTableCell<S> extends TableCell<S, Double> {
    private final double[] permisBarVolts;
    private final Supplier<Integer> barIndexSupplier;

    public PostprocessorTableCell(double[] permisBarVolts, Supplier<Integer> barIndexSupplier) {
        this.permisBarVolts = permisBarVolts;
        this.barIndexSupplier = barIndexSupplier;
    }

    @Override
    protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        setStyle(null);
        setText(null);
        if (item != null && !empty) {
            setText(String.valueOf(item));
            int currentBarIndex = barIndexSupplier.get();
            if (Math.abs(item) >= permisBarVolts[currentBarIndex]) {
                setStyle("-fx-background-color: red");
            }
        }
    }
}
