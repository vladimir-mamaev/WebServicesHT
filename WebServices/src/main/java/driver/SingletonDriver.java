package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class SingletonDriver {
    private SingletonDriver() {
    }

    private static WebDriver instance;

    public static WebDriver getDriver() {
        if (instance == null) {
            System.setProperty("webdriver.chrome.driver", "src/main/java/driver/chromedriver.exe");
            instance = new ChromeDriver();
            instance.manage().window().maximize();
            instance.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }
        return instance;
    }
}
