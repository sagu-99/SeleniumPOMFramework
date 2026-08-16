package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void initialize(){
        setup();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void verifyLogin(){
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLoginButton();
        Assert.assertEquals(driver.getTitle(), "OrangeHRM");
    }

    @AfterMethod
    public void cleanup() {
        tearDown();
    }
}
