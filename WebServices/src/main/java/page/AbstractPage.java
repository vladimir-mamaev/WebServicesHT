package page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.PropertiesUtil;

import static driver.SingletonDriver.getDriver;




public abstract class AbstractPage {
    private static final String BASE_URL= PropertiesUtil.get("url");
    private static final String INITIAL_PAGE_NAME= PropertiesUtil.get("page.name.initial");
    public AbstractPage() {
        PageFactory.initElements(getDriver(), this);
    }

    public void openPage(String name) {
        if (INITIAL_PAGE_NAME.equals(name)) {
            getDriver().get(BASE_URL);
        }
    }

    public void clickButtonUsingJS(WebElement button) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("arguments[0].click();", button);
    }

}
