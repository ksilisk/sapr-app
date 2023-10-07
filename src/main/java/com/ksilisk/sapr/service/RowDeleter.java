package com.ksilisk.sapr.service;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class RowDeleter implements EventHandler<MouseEvent> {
    private final TableView<?> tableView;

    public RowDeleter(TableView<?> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void handle(MouseEvent event) {
        int row = tableView.getFocusModel().getFocusedIndex();
        tableView.getItems().remove(row);
    }
}
