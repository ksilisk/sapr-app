package com.ksilisk.sapr.handler;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Scale;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.RIGHT;

public class ScaleSceneEventHandler implements EventHandler<KeyEvent> {
    private static final int SHIFT_STEP = 10;

    @Override
    public void handle(KeyEvent event) {
        Camera camera = ((Scene) event.getSource()).getCamera();
        switch (event.getCode()) {
            case PLUS -> camera.getTransforms().add(new Scale(0.9, 0.9));
            case MINUS -> camera.getTransforms().add(new Scale(1.1, 1.1));
            case UP, DOWN ->
                    camera.setTranslateY(camera.getTranslateY() + SHIFT_STEP * (event.getCode() == DOWN ? -1 : 1));
            case LEFT, RIGHT ->
                    camera.setTranslateX(camera.getTranslateX() + SHIFT_STEP * (event.getCode() == RIGHT ? -1 : 1));
            case EQUALS -> {
                if (event.isShiftDown()) {
                    camera.getTransforms().add(new Scale(0.9, 0.9));
                }
            }
        }
    }
}
