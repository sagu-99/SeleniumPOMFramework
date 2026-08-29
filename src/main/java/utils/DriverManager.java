package utils;

import base.TestContext;
import org.openqa.selenium.WebDriver;

/**
 * Thread-safe driver storage for the framework.
 *
 * <p>The framework uses ThreadLocal state so multiple test threads do not share the same WebDriver.
 * This is important when running tests in parallel.
 */
public final class DriverManager {

    private DriverManager() {
    }

    /**
     * Returns the driver for the current thread.
     */
    public static WebDriver getDriver() {
        return TestContext.getDriver();
    }

    /**
     * Saves the current thread's WebDriver instance.
     */
    public static void setDriver(WebDriver driver) {
        TestContext.setDriver(driver);
    }

    /**
     * Removes the WebDriver from the current thread context.
     */
    public static void remove() {
        TestContext.clear();
    }

    /**
     * Quits the driver and clears it from the thread context.
     */
    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
            }
            TestContext.clear();
        }
    }
}