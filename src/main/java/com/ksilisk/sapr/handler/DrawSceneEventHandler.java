package com.ksilisk.sapr.handler;

import com.ksilisk.sapr.config.SaprAppConfig;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import static com.ksilisk.sapr.config.SaprAppConfig.ADDITIONAL_INFO;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.RIGHT;

public class DrawSceneEventHandler implements EventHandler<KeyEvent> {
    private final int shiftStep;

    public DrawSceneEventHandler() {
        this.shiftStep = SaprAppConfig.getInstance().getPreprocessorDrawScaleShiftStep();
    }

    @Override
    public void handle(KeyEvent event) {
        Camera camera = ((Scene) event.getSource()).getCamera();
        switch (event.getCode()) {
            case PLUS -> camera.getTransforms().add(new Scale(0.9, 0.9));
            case MINUS -> camera.getTransforms().add(new Scale(1.1, 1.1));
            case UP, DOWN ->
                    camera.setTranslateY(camera.getTranslateY() + shiftStep * (event.getCode() == DOWN ? -1 : 1));
            case LEFT, RIGHT ->
                    camera.setTranslateX(camera.getTranslateX() + shiftStep * (event.getCode() == RIGHT ? -1 : 1));
            case EQUALS -> {
                if (event.isShiftDown()) {
                    camera.getTransforms().add(new Scale(0.9, 0.9));
                }
            }
            case ESCAPE -> ((Stage) ((Scene) event.getSource()).getWindow()).close();
            case I -> new Alert(INFORMATION, ADDITIONAL_INFO).show();
        }
    }
}
