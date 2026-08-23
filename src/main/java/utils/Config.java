package utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Simple configuration singleton.
 * Priority: System property > config.properties file > built-in default
 */
public final class Config {

    private final Properties props = new Properties();

    private Config(){
        // load defaults from classpath resource if present
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if (in != null) props.load(in);
        } catch (Exception ignored) { }
    }

    private static class Holder {
        static final Config INSTANCE = new Config();
    }

    /**
     * Returns the singleton Config instance.
     * Uses the Initialization-on-demand holder idiom: the nested Holder class
     * is only loaded when this method is first called, so INSTANCE is created
     * lazily and safely by the JVM without synchronized blocks.
     */
    public static Config get() {
        return Holder.INSTANCE;
    }

    public String getBaseUrl(){
        // system property takes precedence
        String url = System.getProperty("baseUrl");
        if (url != null && !url.isBlank()) return url;
        url = props.getProperty("base.url");
        if (url != null && !url.isBlank()) return url;
        // fallback hard-coded (should rarely be needed)
        return "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    }

    public String getDefaultBrowser(){
        String browser = System.getProperty("browser");
        if (browser != null && !browser.isBlank()) return browser;
        browser = props.getProperty("default.browser");
        return browser == null ? "chrome" : browser;
    }
}
