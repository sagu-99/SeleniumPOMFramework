package utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Centralized configuration holder for the framework.
 *
 * <p>Configuration precedence is:
 * 1. JVM/system properties (for example -Dbrowser=firefox)
 * 2. active environment profile from src/main/resources/profiles/<env>.properties
 * 3. base config.properties
 * 4. hard-coded fallback values inside the framework
 *
 * <p>This class is implemented as a singleton so the same configuration is reused across the test suite.
 */
public final class Config {

    /**
     * Internal property store for all loaded config values.
     */
    private final Properties props = new Properties();

    /**
     * Private constructor loads the default configuration and environment-specific overrides.
     */
    private Config() {
        loadFromResource("config.properties");

        String environment = resolveValue("environment", "qa");
        loadFromResource("profiles/" + environment + ".properties");

        String profile = resolveValue("profile", null);
        if (profile != null && !profile.isBlank()) {
            loadFromResource("profiles/" + profile + ".properties");
        }
    }

    /**
     * Holder pattern provides lazy, thread-safe singleton initialization.
     */
    private static class Holder {
        static final Config INSTANCE = new Config();
    }

    /**
     * Returns the singleton Config instance.
     */
    public static Config get() {
        return Holder.INSTANCE;
    }

    /**
     * Returns the application base URL.
     * A JVM property like -DbaseUrl=https://... has highest priority.
     */
    public String getBaseUrl() {
        String url = System.getProperty("baseUrl");
        if (url != null && !url.isBlank()) return url;
        url = props.getProperty("base.url");
        if (url != null && !url.isBlank()) return url;
        return "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    }

    /**
     * Returns the default browser for the suite.
     * Example: chrome, firefox, edge.
     */
    public String getDefaultBrowser() {
        String browser = System.getProperty("browser");
        if (browser != null && !browser.isBlank()) return browser;
        browser = props.getProperty("default.browser");
        return browser == null ? "chrome" : browser.toLowerCase();
    }

    /**
     * Returns the active environment name. Example: qa, uat, prod.
     */
    public String getEnvironment() {
        return resolveValue("environment", "qa");
    }

    /**
     * Returns a specific profile name if present. Example: default, regression, smoke.
     */
    public String getProfile() {
        return resolveValue("profile", "default");
    }

    /**
     * Returns implicit wait in seconds.
     */
    public int getImplicitWaitSeconds() {
        return getInt("implicit.wait.seconds", 10);
    }

    /**
     * Returns explicit wait timeout in seconds.
     */
    public int getExplicitWaitSeconds() {
        return getInt("explicit.wait.seconds", 15);
    }

    /**
     * Returns true when the framework should run browser in headless mode.
     */
    public boolean isHeadless() {
        return getBoolean("headless", false);
    }

    /**
     * Reads an integer property and falls back if not found or invalid.
     */
    public int getInt(String key, int defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = props.getProperty(key);
        }
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Reads a boolean property and falls back if not found.
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = props.getProperty(key);
        }
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }

    /**
     * Resolves a property from JVM/system properties or the loaded property file.
     */
    private String resolveValue(String key, String fallback) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = props.getProperty(key);
        }
        if (value == null || value.isBlank()) {
            return fallback == null ? null : fallback.toLowerCase();
        }
        return value.toLowerCase();
    }

    /**
     * Loads a properties file from classpath.
     */
    private void loadFromResource(String resourceName) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (in != null) {
                props.load(in);
            }
        } catch (Exception ignored) {
            // Ignore missing config files. A valid fallback is already available in code.
        }
    }
}
