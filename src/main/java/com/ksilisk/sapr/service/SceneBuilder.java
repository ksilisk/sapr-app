package com.ksilisk.sapr.service;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Builder;

public class SceneBuilder implements Builder<Scene> {
    private Parent parent;
    private double width = -1;
    private double height = -1;

    public SceneBuilder parent(Parent parent) {
        this.parent = parent;
        return this;
    }

    public SceneBuilder width(double width) {
        this.width = width;
        return this;
    }

    public SceneBuilder height(double height) {
        this.height = height;
        return this;
    }

    @Override
    public Scene build() {
        return new Scene(parent, width, height);
    }
}
