package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPageWithPageFactory {

    private WebDriver driver;

    public LoginPageWithPageFactory(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //Approach1
    @FindBy(name="username")
    WebElement usernameTextbox;

    //Approach2
   /* @FindBy(how= How.NAME, using="username")
    WebElement usernameTextbox;*/

    @FindBy(name="password")
    WebElement passwordTextbox;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginButton;



    public void enterUsername(String user){
        usernameTextbox.sendKeys(user);
    }

    public void enterPassword(String pwd) {
        passwordTextbox.sendKeys(pwd);
    }

    public void clickLoginButton(){
        loginButton.click();
    }

}
