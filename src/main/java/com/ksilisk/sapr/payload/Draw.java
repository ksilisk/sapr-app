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
        private static final double DEFAULT_DRAW_WIDTH = 700;
        private static final double DEFAULT_DRAW_HEIGHT = 500;
        private static final double DEFAULT_MARGIN = 20;
        private static final double NODE_LOAD_LENGTH = 25;
        private static final double MIN_BAR_LENGTH = 60;
        private static final double MIN_BAR_AREA = 30;
        private static final double SUPPORT_HEIGHT = 30;
        private static final double SUPPORT_WIDTH = 10;
        private static final Color SUPPORT_COLOR = Color.GREEN;
        private static final Color BAR_LOAD_COLOR = Color.BLUE;
        private static final Color NODE_LOAD_COLOR = Color.RED;
        private static final double BAR_LOAD_SUB_VECTOR_LENGTH = 10;
        private final List<Bar> bars = new ArrayList<>();
        private final List<com.ksilisk.sapr.payload.Node> nodes = new ArrayList<>();
        private double margin = DEFAULT_MARGIN;
        private double width = DEFAULT_DRAW_WIDTH;
        private double height = DEFAULT_DRAW_HEIGHT;
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
                createBarLoad(x, bar.getWidth(), bars.get(barInd).getXLoad())
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
            Path loadVector = createXVector(x, node.getXLoad() > 0, NODE_LOAD_LENGTH);
            loadVector.setStroke(NODE_LOAD_COLOR);
            loadVector.setViewOrder(-1);
            return node.getXLoad() != 0
                    ? Optional.of(loadVector)
                    : Optional.empty();
        }

        private Optional<Path> createBarLoad(double x, double barLength, double barLoad) {
            if (barLoad == 0) {
                return Optional.empty();
            }
            boolean positive = barLoad > 0;
            int sign = 1;
            if (!positive) {
                x += barLength;
                sign = -1;
            }
            int countSubVectors = (int) (barLength / BAR_LOAD_SUB_VECTOR_LENGTH);
            List<Path> loadSubVectors = new ArrayList<>();
            while (countSubVectors > 1) {
                loadSubVectors.add(createXVector(x, positive, BAR_LOAD_SUB_VECTOR_LENGTH));
                countSubVectors--;
                x += BAR_LOAD_SUB_VECTOR_LENGTH * sign;
            }
            if (countSubVectors > 0) {
                loadSubVectors.add(createXVector(x, positive, BAR_LOAD_SUB_VECTOR_LENGTH + (barLength % BAR_LOAD_SUB_VECTOR_LENGTH)));
            }
            return loadSubVectors.stream()
                    .reduce((v1, v2) -> {
                        v1.getElements().addAll(v2.getElements());
                        return v1;
                    })
                    .map(path -> {
                        path.setStroke(BAR_LOAD_COLOR);
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
            Path path = new Path(moveTo, hLineTo, lineTo, moveTo1, lineTo1);
            path.setStrokeWidth(2);
            return path;
        }

        private Rectangle createBar(double x, Bar bar) {
            double barLength = bar.getLength();
            double barArea = bar.getArea();
            if (barLength >= width) {
                barLength = width / 2;
            }
            if (barLength < MIN_BAR_LENGTH) {
                barLength = MIN_BAR_LENGTH;
            }
            if (barArea >= height) {
                barArea = height / 3;
            }
            if (barArea < MIN_BAR_AREA) {
                barArea = MIN_BAR_AREA;
            }
            return new Rectangle(x, (height / 2) - (barArea / 2), barLength, barArea);
        }

        private Rectangle createSupport(double x) {
            Rectangle rectangle = new Rectangle(x, (height / 2) - (SUPPORT_HEIGHT / 2), SUPPORT_WIDTH, SUPPORT_HEIGHT);
            rectangle.setFill(SUPPORT_COLOR);
            return rectangle;
        }
    }
}
