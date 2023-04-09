package helper;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertiesUtil;

import static driver.SingletonDriver.getDriver;


public class WebDriverWaiter {
    private static final int IMPLICITLY_WAIT_TIMEOUT = Integer.parseInt(PropertiesUtil.get("implicitly.wait.timeout"));
    protected WebDriverWait wait;

    public WebDriverWaiter() {
        wait = new WebDriverWait(getDriver(), IMPLICITLY_WAIT_TIMEOUT);
    }

    public static void waitForPageReadyState(int timeToWait) {
        new WebDriverWait(getDriver(), timeToWait).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

}
