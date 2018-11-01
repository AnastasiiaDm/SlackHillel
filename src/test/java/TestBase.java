import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import static java.lang.System.setProperty;

public class TestBase {
    public static WebDriver browser;
    public static Helper h;

    @BeforeTest
    public static void openBrowser() {
        setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        browser = new ChromeDriver(options);

        browser.get(TestData.baseURL);

        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

        h = new Helper(browser);
    }

    @AfterTest
    public static void closeBrowser() {
        browser.quit();

    }


//    FluentWait example

//    List<WebElement> buttonProfile =
//        new FluentWait<>(browser)
//                .withTimeout(Duration.ofSeconds(7))
//                .pollingEvery(Duration.ofMillis(500))
//                .ignoring(InvalidElementStateException.class)
//                .until(browser -> browser.findElements(By.cssSelector("span#team_menu_user_name")));

}
