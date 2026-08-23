package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;
import utils.Log;

public class LoginPage {

    private WebDriver driver;

    private By usernameTextbox = By.name("username");
    private By passwordTextbox = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");

    public LoginPage(){
        this.driver = DriverManager.getDriver();
    }

    public void enterUsername(String user){
        driver.findElement(usernameTextbox).sendKeys(user);
    }

    public void enterPassword(String pwd) {
        driver.findElement(passwordTextbox).sendKeys(pwd);
    }

    public void clickLoginButton(){
        Log.info("Clicking on login button");
        driver.findElement(loginButton).click();
    }

}
