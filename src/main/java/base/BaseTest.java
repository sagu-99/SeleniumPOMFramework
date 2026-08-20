package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ExtentReportManager;
import utils.Log;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setupReport(){
        extent = ExtentReportManager.getReportInstance();
    }

    @AfterSuite
    public void teardownReport(){
        extent.flush();
    }

    @BeforeMethod
    public void setup(){
        Log.info("Setting up WebDriver...");
        driver = new ChromeDriver();
        Log.info("Navigating to Test Url");
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ExtentReportManager.captureScreenshot(driver, "LoginFailure");
            System.out.println("Screenshot Captures, PATH:" + screenshotPath);
            test.fail("Test Failed. Check Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        if(driver!=null){
            Log.info("Closing WebDriver...");
            driver.quit();
        }
    }

}
