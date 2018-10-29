import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import static java.lang.System.setProperty;

public class TestBase {public static WebDriver browser;
    public static Helper h;

    @BeforeTest
    public static void openBrowser() {
        setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");

        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

        h = new Helper(browser);
    }

//    @AfterTest
//    public static void closeBrowser() {
//        browser.quit();
//
//    }

}
