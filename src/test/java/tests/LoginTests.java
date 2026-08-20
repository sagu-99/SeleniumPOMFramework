package tests;

import base.BaseTest;
import com.beust.jcommander.Parameter;
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
        ExcelUtils.loadExcelFile(filePath, sheetName);
        int rowCount = ExcelUtils.getRowCount();
        Object[][] loginData = new Object[rowCount - 1][2];

        for (int i = 1; i < rowCount; i++) {
            loginData[i - 1][0] = ExcelUtils.getCellData(i, 0); // Username
            loginData[i - 1][1] = ExcelUtils.getCellData(i, 1); // Password
        }
        ExcelUtils.closeWorkbook();
        return loginData;
    }

    @DataProvider(name = "loginData2")
    public Object[][] getData(){
        return new Object[][]{
                {"Admin", "admin123"},
                {"Admin", "admin1234"},
                {"Admin1", "admin123"},
                {"Admin1", "admin1234"}
        };
    }

    @Test(dataProvider = "loginData")
    public void verifyLogin(String username, String password) {
        Log.info("Starting login test");
        test = ExtentReportManager.createTest("Login Test --"+ username);
        loginPage = new LoginPage(driver);
        test.info("Navigated to Login Page");
        test.info("Entering Credentials");
//        loginPage.enterUsername("Admin");
//        loginPage.enterPassword("admin123");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        test.info("Entered Credentials and clicked login button");
        Log.info("Verifying page title");
        test.info("Verifying page title");
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
        Log.info("Login test completed");
        test.pass("Login Test Successfully passed");
    }

   // @Test(dataProvider = "loginData2")
    @Test
    @Parameters({"username", "password"})
    public void verifyLoginFunction(String username, String password) {
        Log.info("Starting login test");
        test = ExtentReportManager.createTest("Login Test --"+ username);
        loginPage = new LoginPage(driver);
        test.info("Navigated to Login Page");
        test.info("Entering Credentials");
//        loginPage.enterUsername("Admin");
//        loginPage.enterPassword("admin123");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        test.info("Entered Credentials and clicked login button");
        Log.info("Verifying page title");
        test.info("Verifying page title");
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
        Log.info("Login test completed");
        test.pass("Login Test Successfully passed");
    }
}
