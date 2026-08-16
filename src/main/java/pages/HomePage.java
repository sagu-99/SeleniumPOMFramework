package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="offers")
    WebElement offersMenu;

    @FindBy(id="orders")
    WebElement ordersMenu;

    @FindBy(id="favourites")
    WebElement favouritesMenu;

    @FindBy(xpath="//input[@placeholder='Search']")
    WebElement searchTextbox;

    @FindBy(xpath="//button[text()='Search']")
    WebElement  searchButton;

    @FindBy(id="signin")
    WebElement signIn;

    @FindBy(xpath="///option[normalize-space()='Select']")
    WebElement orderByDropdown;

    public void navigateToFavourites(){
        favouritesMenu.click();
    }

    public void navigateToOrders(){
        ordersMenu.click();
    }

    public void searchProduct(String productName){
        searchTextbox.sendKeys("productName");
    }

    public void clickVendorFilter(String vendorName) {
        WebElement vendorFilter = driver.findElement(
                By.xpath("//span[normalize-space()='" + vendorName + "']")
        );
        vendorFilter.click();
    }

    public void clickSignIn(){
        signIn.click();
    }

}
