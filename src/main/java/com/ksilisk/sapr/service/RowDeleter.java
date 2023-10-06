package com.ksilisk.sapr.service;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class RowDeleter<T> implements EventHandler<MouseEvent> {
    private final TableView<T> tableView;

    public RowDeleter(TableView<T> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void handle(MouseEvent event) {
        int row = tableView.getFocusModel().getFocusedIndex();
        tableView.getItems().remove(row);
    }
}
