package com.ksilisk.sapr.payload;

import java.util.List;

public record Construction(
        List<Bar> bars,
        List<Node> nodes,
        boolean leftSupport,
        boolean rightSupport) {
}
