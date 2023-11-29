package com.ksilisk.sapr.annotation;

import javafx.application.Platform;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

class JavaFXTestExtension implements BeforeAllCallback {
    private static volatile boolean isPlatformStarted = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!isPlatformStarted) {
            Platform.startup(() -> {
            });
            isPlatformStarted = true;
        }
    }
}
