package pages;

import base.TestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageWithPageFactory {

   private final WebDriver driver;

   public LoginPageWithPageFactory() {
       this.driver = TestContext.getDriver();
       if (this.driver == null) {
           throw new IllegalStateException("WebDriver not initialized. Ensure BaseTest @BeforeMethod ran first.");
       }
       PageFactory.initElements(this.driver, this);
   }

   @FindBy(name = "username")
   private WebElement usernameTextbox;

   @FindBy(name = "password")
   private WebElement passwordTextbox;

   @FindBy(xpath = "//button[@type='submit']")
   private WebElement loginButton;

   public void enterUsername(String user) {
       usernameTextbox.clear();
       usernameTextbox.sendKeys(user);
   }

   public void enterPassword(String pwd) {
       passwordTextbox.clear();
       passwordTextbox.sendKeys(pwd);
   }

   public void clickLoginButton() {
       loginButton.click();
   }

   public void loginAs(String user, String password) {
       enterUsername(user);
       enterPassword(password);
       clickLoginButton();
   }
}
