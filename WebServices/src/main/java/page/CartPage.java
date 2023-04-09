package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends AbstractPage {
    @FindBy(xpath = "//a[@class='product-summary__img-link']")
    private WebElement productLink;

    public WebElement getProductLink() {
        return productLink;
    }

}
