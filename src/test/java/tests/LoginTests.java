package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.ExcelDataRepository;
import utils.ExtentReportManager;
import utils.Log;
import utils.RetryAnalyzer;
import utils.TestDataProvider;

import java.io.File;

public class LoginTests extends BaseTest {

   private LoginPage loginPage;

   @BeforeMethod
   public void initPage() {
       loginPage = new LoginPage();
    }

   @DataProvider(name = "loginData", parallel = true)
   public Object[][] getLoginData() {
       return TestDataProvider.loginData();
   }

   @DataProvider(name = "loginData2", parallel = true)
   public Object[][] getData() {
       return new Object[][] {
               {"Admin", "admin123"},
               {"Admin", "admin1234"},
               {"Admin1", "admin123"},
               {"Admin1", "admin1234"}
       };
   }

   @Test(dataProvider = "loginData", retryAnalyzer = RetryAnalyzer.class)
   public void verifyLogin(java.util.Map<String, String> data) {
       String username = data.getOrDefault("username", data.getOrDefault("user", ""));
       String password = data.getOrDefault("password", data.getOrDefault("pass", ""));

       Log.info("Starting login test");
       ExtentReportManager.getTest().info("Navigated to Login Page");
       ExtentReportManager.getTest().info("Entering Credentials");

       loginPage.loginAs(username, password);

       ExtentReportManager.getTest().info("Entered Credentials and clicked login button");
       Log.info("Verifying page title");
       ExtentReportManager.getTest().info("Verifying page title");

       new utils.WaitHelper().waitForTitle(getDriver(), "OrangeHRM", 10);
       Assert.assertEquals(getDriver().getTitle(), "OrangeHRM");
       Log.info("Login test completed");
       ExtentReportManager.getTest().pass("Login Test Successfully passed");
   }

   @Test(retryAnalyzer = RetryAnalyzer.class)
   @Parameters({"username", "password"})
   public void verifyLoginFunction(@Optional("Admin") String username, @Optional("admin123") String password) {
       Log.info("Starting login test");
       ExtentReportManager.getTest().info("Navigated to Login Page");
       ExtentReportManager.getTest().info("Entering Credentials");

       loginPage.loginAs(username, password);

       ExtentReportManager.getTest().info("Entered Credentials and clicked login button");
       Log.info("Verifying page title");
       ExtentReportManager.getTest().info("Verifying page title");

       Assert.assertEquals(getDriver().getTitle(), "OrangeHRM");
       Log.info("Login test completed");
       ExtentReportManager.getTest().pass("Login Test Successfully passed");
   }
}
