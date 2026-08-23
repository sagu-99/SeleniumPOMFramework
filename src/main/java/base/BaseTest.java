package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverManager;
import utils.ExtentReportManager;
import utils.Log;
import utils.BrowserFactory;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BaseTest extends DriverManager {

    protected static ExtentReports extent;

    @BeforeSuite
    public void setupReport(){
        extent = ExtentReportManager.getReportInstance();
    }

    @AfterSuite
    public void teardownReport(){
        extent.flush();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void setup(@Optional("") String browser){
        // if browser param not provided, fall back to config default
        if (browser == null || browser.isBlank()) {
            browser = utils.Config.get().getDefaultBrowser();
        }
        Log.info("Setting up WebDriver for browser: " + browser);
        // create and store a WebDriver instance for the current thread
        utils.Browser browserEnum = utils.Browser.from(browser);
        WebDriver wd = BrowserFactory.createDriver(browserEnum);
        setDriver(wd);

        Log.info("Navigating to Test Url");
        String baseUrl = utils.Config.get().getBaseUrl();
        getDriver().get(baseUrl);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        getDriver().manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result){
        if(getDriver() != null){
            if(result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = ExtentReportManager.captureScreenshot(getDriver(), "LoginFailure");
                System.out.println("Screenshot Captures, PATH:" + screenshotPath);
                if (ExtentReportManager.getTest() != null) {
                    ExtentReportManager.getTest().fail("Test Failed. Check Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                }
            }
            Log.info("Closing WebDriver...");
           quitDriver();
        }
    }
}
