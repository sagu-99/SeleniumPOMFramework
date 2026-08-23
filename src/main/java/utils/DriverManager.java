package utils;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void setDriver(WebDriver d){
        driver.set(d);
    }

    public static void remove(){
        driver.remove();
    }

    public static void quitDriver(){
        WebDriver wd = driver.get();
        if(wd != null){
            try { wd.quit(); } catch (Exception ignored) {}
            driver.remove();
        }
    }
}