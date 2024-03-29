package com.ksilisk.sapr.config;

import com.ksilisk.sapr.service.ProcessorService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

public class SaprAppConfig {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProcessorService.class);

    // Defaults
    public static final String DEFAULT_APPLICATION_CONFIGS_PATH = getResourcesPath();
    private static final String DEFAULT_PROCESSOR_VIEW_FILE = "processor-view.fxml";
    private static final String DEFAULT_PRE_PROCESSOR_VIEW_FILE = "preprocessor-view.fxml";
    private static final String DEFAULT_POST_PROCESSOR_VIEW_FILE = "postprocessor-view.fxml";
    private static final String DEFAULT_MAIN_VIEW_FILE = "main-view.fxml";
    private static final String DEFAULT_PREPROCESSOR_DRAW_HEIGHT = "500";
    private static final String DEFAULT_PREPROCESSOR_DRAW_WIDTH = "700";
    private static final String DEFAULT_PREPROCESSOR_DRAW_MARGIN = "20";
    private static final String DEFAULT_DRAW_SCALE_SHIFT_STEP = "10";

    // Configuration files
    public static final String APPLICATION_PROPERTIES_FILE = "sapr-app.properties";
    public static final String APPLICATION_CONFIGS_PATH = "config.path";

    // Preprocessor Configs
    public static final String PREPROCESSOR_DRAW_HEIGHT = "sapr.preprocessor.draw.height";
    public static final String PREPROCESSOR_DRAW_WIGHT = "sapr.preprocessor.draw.width";
    public static final String PREPROCESSOR_DRAW_MARGIN = "sapr.preprocessor.draw.margin";
    public static final String PREPROCESSOR_DRAW_SCALE_SHIFT_STEP = "sapr.preprocessor.draw.scale.shift-step";

    // Views
    public static final String PROCESSOR_VIEW_FILE = "sapr.processor.view.file";
    public static final String PRE_PROCESSOR_VIEW_FILE = "sapr.preprocessor.view.file";
    public static final String POST_PROCESSOR_VIEW_FILE = "sapr.postprocessor.view.file";
    public static final String MAIN_VIEW_FILE = "sapr.main.view.file";

    // Constants
    public static final String APP_NAME = "SAPR-APP v1.0";
    public static final String ADDITIONAL_INFO = "Developed by Shaliko Salimov (ksilisk) in 2023";
    public static final File USER_HOME_DIRECTORY = new File(System.getProperty("user.home"));

    private static SaprAppConfig INSTANCE;
    private static boolean isLoaded = false;

    private final Properties properties = new Properties();

    private SaprAppConfig(Properties properties) {
        this.properties.putAll(properties);
    }

    public static synchronized SaprAppConfig getInstance() {
        if (isLoaded) {
            return INSTANCE;
        }
        throw new IllegalStateException("Configuration is not loaded");
    }

    private static String getResourcesPath() {
        URL resourcesURL = ClassLoader.getSystemResource(".");
        if (resourcesURL == null) {
            log.warn("System resources dir is null");
        }
        return resourcesURL == null ? "conf" : resourcesURL.getPath();
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
        INSTANCE = new SaprAppConfig(properties);
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

    public String getApplicationConfigsPath() {
        return properties.getProperty(APPLICATION_CONFIGS_PATH, DEFAULT_APPLICATION_CONFIGS_PATH);
    }

    public double getPreprocessorDrawHeight() {
        return Double.parseDouble(properties.getProperty(PREPROCESSOR_DRAW_HEIGHT, DEFAULT_PREPROCESSOR_DRAW_HEIGHT));
    }

    public double getPreprocessorDrawWidth() {
        return Double.parseDouble(properties.getProperty(PREPROCESSOR_DRAW_WIGHT, DEFAULT_PREPROCESSOR_DRAW_WIDTH));
    }

    public double getPreprocessorDrawMargin() {
        return Double.parseDouble(properties.getProperty(PREPROCESSOR_DRAW_MARGIN, DEFAULT_PREPROCESSOR_DRAW_MARGIN));
    }

    public int getPreprocessorDrawScaleShiftStep() {
        return Integer.parseInt(properties.getProperty(PREPROCESSOR_DRAW_SCALE_SHIFT_STEP, DEFAULT_DRAW_SCALE_SHIFT_STEP));
    }
}
