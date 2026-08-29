package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Log;

/**
 * Page class for the login page.
 *
 * <p>This class abstracts the login page UI and exposes actions such as entering credentials
 * and clicking the login button.
 */
public class LoginPage extends BasePage {

    @FindBy(name = "username")
    private WebElement usernameTextbox;

    @FindBy(name = "password")
    private WebElement passwordTextbox;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    /**
     * Initializes the page object and binds the WebElements to the current driver.
     */
    public LoginPage() {
        super();
        PageFactory.initElements(driver, this);
    }

    /**
     * Enters the username into the username field.
     */
    public void enterUsername(String user) {
        usernameTextbox.clear();
        usernameTextbox.sendKeys(user);
    }

    /**
     * Enters the password into the password field.
     */
    public void enterPassword(String pwd) {
        passwordTextbox.clear();
        passwordTextbox.sendKeys(pwd);
    }

    /**
     * Clicks the login button.
     */
    public void clickLoginButton() {
        Log.info("Clicking on login button");
        loginButton.click();
    }

    /**
     * Performs the full login flow with one method call.
     */
    public void loginAs(String user, String password) {
        enterUsername(user);
        enterPassword(password);
        clickLoginButton();
    }
}
