package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getReportInstance(){
        if(extent==null){
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = System.getProperty("user.dir")+"\\reports\\ExtentReport_"+timeStamp+".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Test Execution Report");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName){
        ExtentTest t = getReportInstance().createTest(testName);
        extentTest.set(t);
        return t;
    }

    public static ExtentTest getTest(){
        return extentTest.get();
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName){
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir")+"/screenshots/"+screenshotName+".png";
            FileUtils.copyFile(src, new File(path));
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}