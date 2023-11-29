package com.ksilisk.sapr.handler;

import com.ksilisk.sapr.annotation.JavaFXTest;
import javafx.scene.control.TableView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@JavaFXTest
class RowDeleteEventHandlerTest {
    @Test
    void deleteValidRowEventHandle_shouldDelete() {
        // given
        TableView<Integer> tableView = new TableView<>();
        List<Integer> testData = List.of(1, 2, 3, 4, 5);
        tableView.getItems().addAll(testData);
        RowDeleteEventHandler handler = new RowDeleteEventHandler(tableView);
        // when
        tableView.getFocusModel().focus(0);
        handler.handle(null);
        tableView.getFocusModel().focus(2);
        handler.handle(null);
        // then
        Assertions.assertIterableEquals(List.of(2, 3, 5), tableView.getItems());
    }
}