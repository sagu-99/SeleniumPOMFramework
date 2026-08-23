package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.ExcelUtils;
import utils.ExtentReportManager;
import utils.Log;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String filePath = System.getProperty("user.dir")+"/testdata/TestData.xlsx";
        String sheetName = "Sheet1";
        return ExcelUtils.getDataProviderFromExcel(filePath, sheetName);
    }

    @DataProvider(name = "loginData2", parallel = true)
    public Object[][] getData(){
        return new Object[][]{
                {"Admin", "admin123"},
                {"Admin", "admin1234"},
                {"Admin1", "admin123"},
                {"Admin1", "admin1234"}
        };
    }

    @Test(dataProvider = "loginData")
    public void verifyLogin(java.util.Map<String, String> data) {
        String username = data.getOrDefault("username", data.getOrDefault("user", ""));
        String password = data.getOrDefault("password", data.getOrDefault("pass", ""));
        Log.info("Starting login test");
        loginPage = new LoginPage();
        ExtentReportManager.getTest().info("Navigated to Login Page");
        ExtentReportManager.getTest().info("Entering Credentials");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        ExtentReportManager.getTest().info("Entered Credentials and clicked login button");
        Log.info("Verifying page title");
        ExtentReportManager.getTest().info("Verifying page title");
        // wait for title to be updated to avoid race in parallel runs
        new utils.WaitHelper().waitForTitle(getDriver(), "OrangeHRM", 10);
        Assert.assertEquals(getDriver().getTitle(), "OrangeHRM");
        Log.info("Login test completed");
        ExtentReportManager.getTest().pass("Login Test Successfully passed");
    }

   // @Test(dataProvider = "loginData2")
    @Test
    @Parameters({"username", "password"})
    public void verifyLoginFunction(@Optional("Admin") String username, @Optional("admin123") String password) {
        Log.info("Starting login test");
        loginPage = new LoginPage();
        ExtentReportManager.getTest().info("Navigated to Login Page");
        ExtentReportManager.getTest().info("Entering Credentials");
//        loginPage.enterUsername("Admin");
//        loginPage.enterPassword("admin123");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        ExtentReportManager.getTest().info("Entered Credentials and clicked login button");
        Log.info("Verifying page title");
        ExtentReportManager.getTest().info("Verifying page title");
        Assert.assertEquals(getDriver().getTitle(), "OrangeHRM");
        Log.info("Login test completed");
        ExtentReportManager.getTest().pass("Login Test Successfully passed");
    }
}
