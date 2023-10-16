package com.ksilisk.sapr.builder;

import com.ksilisk.sapr.service.Bar;
import com.ksilisk.sapr.service.Draw;
import com.ksilisk.sapr.service.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DrawBuilder implements Builder<Draw> {
    private static final double NODE_LOAD_LENGTH = 30;
    private static final double SUPPORT_HEIGHT = 30;
    private static final double SUPPORT_WIDTH = 10;
    private static final Color SUPPORT_COLOR = Color.GREEN;
    private final List<Bar> bars = new ArrayList<>();
    private final List<Node> nodes = new ArrayList<>();
    private double margin = 20;
    private double width;
    private double height;
    private boolean leftSupport;
    private boolean rightSupport;

    public DrawBuilder leftSupport(boolean exists) {
        this.leftSupport = exists;
        return this;
    }

    public DrawBuilder rightSupport(boolean exists) {
        this.rightSupport = exists;
        return this;
    }

    public DrawBuilder bar(Bar bar) {
        this.bars.add(bar);
        return this;
    }

    public DrawBuilder bars(Collection<Bar> bars) {
        this.bars.addAll(bars);
        return this;
    }

    public DrawBuilder node(Node node) {
        this.nodes.add(node);
        return this;
    }

    public DrawBuilder nodes(Collection<Node> nodes) {
        this.nodes.addAll(nodes);
        return this;
    }

    public DrawBuilder width(double width) {
        this.width = width;
        return this;
    }

    public DrawBuilder height(double height) {
        this.height = height;
        return this;
    }

    public DrawBuilder margin(double margin) {
        this.margin = margin;
        return this;
    }

    public Draw build() {
        if (height <= 0) {
            throw new IllegalArgumentException("Draw height must be greater than 0");
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Draw width must be greater than 0");
        }
        if (margin <= 0) {
            throw new IllegalArgumentException("Draw margin must be greater than 0");
        }
        double x = margin;
        Draw draw = new Draw();
        if (leftSupport) {
            draw.getChildren().add(createSupport(x));
            x += SUPPORT_WIDTH;
        }
        for (int barInd = 0; barInd < bars.size(); barInd++) {
            Rectangle bar = createBar(x, bars.get(barInd));
            List<Path> barLoad = createBarLoad(x, bars.get(barInd));
            List<Path> nodeLoad = createNodeLoad(x, nodes.get(barInd));
            x += bar.getWidth();
            draw.getChildren().add(bar);
            draw.getChildren().addAll(barLoad);
            draw.getChildren().addAll(nodeLoad);
        }
        draw.getChildren().addAll(createNodeLoad(x, nodes.get(nodes.size() - 1)));
        if (rightSupport) {
            draw.getChildren().add(createSupport(x));
        }
        return draw;
    }

    private List<Path> createNodeLoad(double x, Node node) {
        List<Path> loads = new ArrayList<>(2);
        if (node.XLoad() != 0) {
            loads.add(createXVector(x, node.XLoad() > 0));
        }
        if (node.YLoad() != 0) {
            loads.add(createYVector(x, node.YLoad() > 0));
        }
        return loads;
    }

    private Path createYVector(double x, boolean positive) {
        int sign = positive ? 1 : -1;
        MoveTo moveTo = new MoveTo(x, height / 2);
        VLineTo vLineTo = new VLineTo((height / 2) + (NODE_LOAD_LENGTH * sign));
        double newHeight = (height / 2) + (NODE_LOAD_LENGTH * sign);
        LineTo lineTo = new LineTo(x - 3, newHeight + (3 * (-sign)));
        MoveTo moveTo1 = new MoveTo(x, newHeight);
        LineTo lineTo1 = new LineTo(x + 3, newHeight + (3 * (-sign)));
        Path path = new Path(moveTo, vLineTo, lineTo, moveTo1, lineTo1);
        path.setStrokeWidth(2);
        path.setStroke(Color.RED);
        path.setViewOrder(0);
        return path;
    }

    private Path createXVector(double x, boolean positive) {
        int sign = positive ? 1 : -1;
        MoveTo moveTo = new MoveTo(x, height / 2);
        HLineTo hLineTo = new HLineTo(x + (NODE_LOAD_LENGTH * sign));
        double newWidth = x + (NODE_LOAD_LENGTH * sign);
        LineTo lineTo = new LineTo(newWidth + (3 * (-sign)), (height / 2) - 3);
        MoveTo moveTo1 = new MoveTo(newWidth, height / 2);
        LineTo lineTo1 = new LineTo(newWidth + (3 * (-sign)), (height / 2) + 3);
        Path path = new Path(moveTo, hLineTo, lineTo, moveTo1, lineTo1);
        path.setStrokeWidth(2);
        path.setStroke(Color.RED);
        path.setViewOrder(0);
        return path;
    }

    private List<Path> createBarLoad(double x, Bar bar) {
        // TODO implement this
        return Collections.emptyList();
    }

    private Rectangle createBar(double x, Bar bar) {
        return new Rectangle(x, (height / 2) - (bar.area() / 2), bar.length(), bar.area());
    }

    private Rectangle createSupport(double x) {
        Rectangle rectangle = new Rectangle(x, (height / 2) - (SUPPORT_HEIGHT / 2), SUPPORT_WIDTH, SUPPORT_HEIGHT);
        rectangle.setFill(SUPPORT_COLOR);
        return rectangle;
    }
}
