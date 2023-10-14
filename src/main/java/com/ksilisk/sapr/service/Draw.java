package com.ksilisk.sapr.service;

import com.ksilisk.sapr.builder.DrawBuilder;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.Collection;

public class Draw extends Group {
    public Draw() {
    }

    public Draw(Node... children) {
        super(children);
    }

    public Draw(Collection<Node> children) {
        super(children);
    }

    public static DrawBuilder builder() {
        return new DrawBuilder();
    }
}
