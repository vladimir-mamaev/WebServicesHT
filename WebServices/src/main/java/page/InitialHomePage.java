package page;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InitialHomePage extends AbstractPage {
    @FindBy(xpath = "//button[@id='onetrust-accept-btn-handler']")
    private WebElement acceptCookies;

    @FindBy(xpath = "//a[@class='minicart__wrapper ']")
    private WebElement cartButton;
    public void clickOnAcceptCookies() {
        acceptCookies.click();
    }
    public void clickOnCartButton() {
        cartButton.click();
    }
}
