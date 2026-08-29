package utils;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

/**
 * Factory responsible for creating the browser instance used in a test.
 *
 * <p>It accepts browser name or enum and delegates the actual creation to the DriverFactory,
 * while BrowserOptionsFactory prepares browser-specific configuration such as headless mode.
 */
public final class BrowserFactory {

    private BrowserFactory() {
    }

    /**
     * Creates a WebDriver using the browser name argument.
     *
     * @param browserName Example: chrome, firefox, edge
     * @return driver instance for the selected browser
     */
    public static WebDriver createDriver(String browserName) {
        return createDriver(Browser.from(browserName));
    }

    /**
     * Creates a WebDriver using the Browser enum.
     *
     * @param browser Browser enum value
     * @return driver instance for the selected browser
     */
    public static WebDriver createDriver(Browser browser) {
        if (browser == null) {
            browser = Browser.CHROME;
        }
        MutableCapabilities options = BrowserOptionsFactory.getOptions(browser);
        return DriverFactory.create(browser, options);
    }
}