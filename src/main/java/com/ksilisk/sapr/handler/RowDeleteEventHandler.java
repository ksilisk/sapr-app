package com.ksilisk.sapr.handler;

import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class RowDeleteEventHandler implements EventHandler<MouseEvent> {
    private final TableView<?> tableView;

    public RowDeleteEventHandler(TableView<?> tableView) {
        this.tableView = tableView;
    }

    @Override
    public void handle(MouseEvent event) {
        int row = tableView.getFocusModel().getFocusedIndex();
        if (row >= 0) {
            tableView.getItems().remove(row);
        }
    }
}
