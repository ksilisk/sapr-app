package com.ksilisk.sapr.service;

import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Scale;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.RIGHT;

public class SceneScaleHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent event) {
        Camera camera = ((Scene) event.getSource()).getCamera();
        switch (event.getCode()) {
            case PLUS -> camera.getTransforms().add(new Scale(0.9, 0.9));
            case MINUS -> camera.getTransforms().add(new Scale(1.1, 1.1));
            case UP, DOWN -> camera.setTranslateY(camera.getTranslateY() + (event.getCode() == DOWN ? -10 : 10));
            case LEFT, RIGHT -> camera.setTranslateX(camera.getTranslateX() + (event.getCode() == RIGHT ? -10 : 10));
            case EQUALS -> {
                if (event.isShiftDown()) {
                    camera.getTransforms().add(new Scale(0.9, 0.9));
                }
            }
        }
    }
}
