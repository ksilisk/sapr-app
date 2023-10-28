package com.ksilisk.sapr.payload;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public static class DrawBuilder implements Builder<Draw> {
        private static final double NODE_LOAD_LENGTH = 30;
        private static final double SUPPORT_HEIGHT = 30;
        private static final double SUPPORT_WIDTH = 10;
        private static final Color SUPPORT_COLOR = Color.GREEN;
        private static final double BAR_LOAD_SUB_VECTOR_LENGTH = 10;
        private final List<Bar> bars = new ArrayList<>();
        private final List<com.ksilisk.sapr.payload.Node> nodes = new ArrayList<>();
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

        public DrawBuilder node(com.ksilisk.sapr.payload.Node node) {
            this.nodes.add(node);
            return this;
        }

        public DrawBuilder nodes(Collection<com.ksilisk.sapr.payload.Node> nodes) {
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
                draw.getChildren().add(bar);
                createNodeLoad(x, nodes.get(barInd))
                        .ifPresent(load -> draw.getChildren().add(load));
                createBarLoad(x, bars.get(barInd))
                        .ifPresent(load -> draw.getChildren().add(load));
                x += bar.getWidth();
            }
            createNodeLoad(x, nodes.get(nodes.size() - 1))
                    .ifPresent(load -> draw.getChildren().add(load));
            if (rightSupport) {
                draw.getChildren().add(createSupport(x));
            }
            return draw;
        }

        private Optional<Path> createNodeLoad(double x, com.ksilisk.sapr.payload.Node node) {
            Path path = createXVector(x, node.getXLoad() > 0, NODE_LOAD_LENGTH);
            path.setStrokeWidth(2);
            path.setStroke(Color.RED);
            path.setViewOrder(-1);
            return node.getXLoad() != 0
                    ? Optional.of(path)
                    : Optional.empty();
        }

        private Optional<Path> createBarLoad(double x, Bar bar) {
            if (bar.getXLoad() == 0) {
                return Optional.empty();
            }
            int sign = 1;
            if (bar.getXLoad() < 0) {
                x += bar.getLength();
                sign = -1;
            }
            int countSubVectors = (int) (bar.getLength() / BAR_LOAD_SUB_VECTOR_LENGTH);
            List<Path> loadSubVectors = new ArrayList<>();
            while (countSubVectors > 1) {
                loadSubVectors.add(createXVector(x, bar.getXLoad() > 0, BAR_LOAD_SUB_VECTOR_LENGTH));
                countSubVectors--;
                x += BAR_LOAD_SUB_VECTOR_LENGTH * sign;
            }
            if (countSubVectors > 0) {
                loadSubVectors.add(createXVector(x, bar.getXLoad() > 0, BAR_LOAD_SUB_VECTOR_LENGTH + (bar.getLength() % BAR_LOAD_SUB_VECTOR_LENGTH)));
            }
            return loadSubVectors.stream()
                    .reduce((v1, v2) -> {
                        v1.getElements().addAll(v2.getElements());
                        return v1;
                    })
                    .map(path -> {
                        path.setStroke(Color.BLUE);
                        path.setStrokeWidth(2);
                        return path;
                    });
        }

        private Path createXVector(double x, boolean positive, double length) {
            int sign = positive ? 1 : -1;
            MoveTo moveTo = new MoveTo(x, height / 2);
            HLineTo hLineTo = new HLineTo(x + (length * sign));
            double newWidth = x + (length * sign);
            LineTo lineTo = new LineTo(newWidth + (3 * (-sign)), (height / 2) - 3);
            MoveTo moveTo1 = new MoveTo(newWidth, height / 2);
            LineTo lineTo1 = new LineTo(newWidth + (3 * (-sign)), (height / 2) + 3);
            return new Path(moveTo, hLineTo, lineTo, moveTo1, lineTo1);
        }

        private Rectangle createBar(double x, Bar bar) {
            return new Rectangle(x, (height / 2) - (bar.getArea() / 2), bar.getLength(), bar.getArea());
        }

        private Rectangle createSupport(double x) {
            Rectangle rectangle = new Rectangle(x, (height / 2) - (SUPPORT_HEIGHT / 2), SUPPORT_WIDTH, SUPPORT_HEIGHT);
            rectangle.setFill(SUPPORT_COLOR);
            return rectangle;
        }
    }
}
