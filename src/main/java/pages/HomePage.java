package pages;

import base.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private final WebDriver driver;

    public HomePage() {
        this.driver = TestContext.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException("WebDriver not initialized. Ensure BaseTest @BeforeMethod ran first.");
        }
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(id = "offers")
    private WebElement offersMenu;

    @FindBy(id = "orders")
    private WebElement ordersMenu;

    @FindBy(id = "favourites")
    private WebElement favouritesMenu;

    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement searchTextbox;

    @FindBy(xpath = "//button[text()='Search']")
    private WebElement searchButton;

    @FindBy(id = "signin")
    private WebElement signIn;

    @FindBy(xpath = "///option[normalize-space()='Select']")
    private WebElement orderByDropdown;

    public void navigateToFavourites() {
        favouritesMenu.click();
    }

    public void navigateToOrders() {
        ordersMenu.click();
    }

    public void searchProduct(String productName) {
        searchTextbox.sendKeys(productName);
    }

    public void clickVendorFilter(String vendorName) {
        WebElement vendorFilter = driver.findElement(
                By.xpath("//span[normalize-space()='" + vendorName + "']")
        );
        vendorFilter.click();
    }

    public void clickSignIn() {
        signIn.click();
    }
}

