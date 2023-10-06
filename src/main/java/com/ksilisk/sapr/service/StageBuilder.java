package com.ksilisk.sapr.service;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Builder;

public class StageBuilder implements Builder<Stage> {
    private boolean resizable = true;
    private String title;
    private Scene scene;
    private Modality modality;

    public StageBuilder resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public StageBuilder title(String title) {
        this.title = title;
        return this;
    }

    public StageBuilder scene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public StageBuilder modality(Modality modality) {
        this.modality = modality;
        return this;
    }

    @Override
    public Stage build() {
        Stage stage = new Stage();
        stage.setResizable(resizable);
        if (title != null) {
            stage.setTitle(title);
        }
        if (scene != null) {
            stage.setScene(scene);
        }
        if (modality != null) {
            stage.initModality(modality);
        }
        return stage;
    }
}
