package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(Config.get().getExplicitWaitSeconds()))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForClickable(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(Config.get().getExplicitWaitSeconds()))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForTitle(WebDriver driver, String title, int timeoutSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.titleIs(title));
    }

    public static void waitForUrlContains(WebDriver driver, String partialUrl) {
        new WebDriverWait(driver, Duration.ofSeconds(Config.get().getExplicitWaitSeconds()))
                .until(ExpectedConditions.urlContains(partialUrl));
    }
}
