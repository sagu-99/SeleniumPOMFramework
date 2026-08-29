package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.Config;
import utils.FrameworkListener;
import utils.BrowserFactory;
import utils.DriverManager;
import utils.ExtentReportManager;
import utils.Log;

import java.time.Duration;

/**
 * Base class for all test classes in this framework.
 *
 * <p>This class is responsible for the common test lifecycle:
 * - report setup before the suite starts
 * - browser creation before each test method
 * - cleanup after each test method
 *
 * <p>Each test class extends this class to inherit the standard setup/teardown behavior.
 */
@Listeners(FrameworkListener.class)
public abstract class BaseTest {
    protected static ExtentReports extent;

    /**
     * Initializes the ExtentReports instance once before the whole suite starts.
     */
    @BeforeSuite
    public void setupReport() {
        extent = ExtentReportManager.getReportInstance();
    }

    /**
     * Flushes the report after the suite is complete.
     */
    @AfterSuite
    public void teardownReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Creates a WebDriver for the current test thread and opens the configured application URL.
     *
     * @param browser Optional browser override passed from TestNG XML or command line.
     */
    @BeforeMethod
    @Parameters({"browser"})
    public void setup(@Optional("") String browser) {
        if (browser == null || browser.isBlank()) {
            browser = utils.Config.get().getDefaultBrowser();
        }

        Log.info("Setting up WebDriver for browser: " + browser);
        WebDriver driver = BrowserFactory.createDriver(browser);
        DriverManager.setDriver(driver);
        TestContext.setDriver(driver);

        String baseUrl = Config.get().getBaseUrl();
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.get().getImplicitWaitSeconds()));
        if (!Config.get().isHeadless()) {
            driver.manage().window().maximize();
        }
    }

    /**
     * Closes the driver and captures a screenshot if the test failed.
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        WebDriver driver = getDriver();
        if (driver != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = ExtentReportManager.captureScreenshot(driver, "LoginFailure");
                if (screenshotPath != null && ExtentReportManager.getTest() != null) {
                    ExtentReportManager.getTest().fail(
                            "Test Failed. Check Screenshot",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
                    );
                }
            }
            Log.info("Closing WebDriver...");
            DriverManager.quitDriver();
            TestContext.clear();
        }
    }

    /**
     * Returns the active WebDriver instance for the current thread.
     */
    protected WebDriver getDriver() {
        return TestContext.getDriver();
    }
}
