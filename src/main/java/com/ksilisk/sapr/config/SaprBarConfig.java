package com.ksilisk.sapr.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class SaprBarConfig {
    // Defaults
    public static final String DEFAULT_APPLICATION_CONFIGS_PATH = "config";
    private static final String DEFAULT_PROCESSOR_VIEW_FILE = "processor-view.fxml";
    private static final String DEFAULT_PRE_PROCESSOR_VIEW_FILE = "preprocessor-view.fxml";
    private static final String DEFAULT_POST_PROCESSOR_VIEW_FILE = "postprocessor-view.fxml";
    private static final String DEFAULT_MAIN_VIEW_FILE = "main-view.fxml";
    private static final String DEFAULT_VALIDATION_ERROR_VIEW = "validation-error-view.fxml";

    // Configuration files
    public static final String APPLICATION_PROPERTIES_FILE = "sapr-bar.properties";
    public static final String APPLICATION_CONFIGS_PATH = "configs-path";

    // Views
    public static final String PROCESSOR_VIEW_FILE = "sapr.processor.view.file";
    public static final String PRE_PROCESSOR_VIEW_FILE = "sapr.preprocessor.view.file";
    public static final String POST_PROCESSOR_VIEW_FILE = "sapr.postprocessor.view.file";
    public static final String MAIN_VIEW_FILE = "sapr.main.view.file";
    public static final String VALIDATION_ERROR_VIEW = "sapr.validation.error.view.file";

    // Constants
    public static final String APP_NAME = "SAPR-BAR v1.0";

    private static SaprBarConfig INSTANCE;
    private static boolean isLoaded = false;

    private final Properties properties = new Properties();

    private SaprBarConfig(Properties properties) {
        this.properties.putAll(properties);
    }

    public static synchronized SaprBarConfig getInstance() {
        if (isLoaded) {
            return INSTANCE;
        }
        throw new IllegalStateException("Configuration is not loaded");
    }

    public static synchronized void load(Map<String, String> parameters) throws IOException {
        if (isLoaded) {
            throw new IllegalStateException("Configuration is already loaded");
        }
        isLoaded = true;
        Properties properties = new Properties();
        properties.putAll(parameters);
        String appConfigsPath = properties.getProperty(APPLICATION_CONFIGS_PATH, DEFAULT_APPLICATION_CONFIGS_PATH);
        properties.load(Files.newInputStream(Paths.get(appConfigsPath, APPLICATION_PROPERTIES_FILE)));
        INSTANCE = new SaprBarConfig(properties);
    }

    public File getProcessorViewFile() {
        String value = properties.getProperty(PROCESSOR_VIEW_FILE, DEFAULT_PROCESSOR_VIEW_FILE);
        return new File(getApplicationConfigsPath(), value);
    }

    public File getPreProcessorViewFile() {
        String value = properties.getProperty(PRE_PROCESSOR_VIEW_FILE, DEFAULT_PRE_PROCESSOR_VIEW_FILE);
        return new File(getApplicationConfigsPath(), value);
    }

    public File getPostProcessorViewFile() {
        String value = properties.getProperty(POST_PROCESSOR_VIEW_FILE, DEFAULT_POST_PROCESSOR_VIEW_FILE);
        return new File(getApplicationConfigsPath(), value);
    }

    public File getMainViewFile() {
        String value = properties.getProperty(MAIN_VIEW_FILE, DEFAULT_MAIN_VIEW_FILE);
        return new File(getApplicationConfigsPath(), value);
    }

    public File getValidationErrorViewFile() {
        String value = properties.getProperty(VALIDATION_ERROR_VIEW, DEFAULT_VALIDATION_ERROR_VIEW);
        return new File(getApplicationConfigsPath(), value);
    }

    public String getApplicationConfigsPath() {
        return properties.getProperty(APPLICATION_CONFIGS_PATH, DEFAULT_APPLICATION_CONFIGS_PATH);
    }
}
