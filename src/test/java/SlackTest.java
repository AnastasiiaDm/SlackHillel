import org.openqa.selenium.By;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SlackTest extends TestBase {
    private static void login(boolean isSuccess) throws InterruptedException {
        browser.get(TestData.baseURL);

        browser.findElement(By.cssSelector("a[data-clog-ui-element='link_sign_in_nav']")).click();
        h.findAndFill(By.cssSelector("input#domain"), TestData.userURL + "\n");

        h.findAndFill(By.cssSelector("input#email"), TestData.username);
        h.findAndFill(By.cssSelector("input#password"), (isSuccess ? TestData.pass : TestData.badPass) + "\n");
    }

    @Test()
    public static void loginFail() throws InterruptedException {
        login(false);

        // Some error message is present
        List<WebElement> errorMessages =
                browser.findElements
                        (By.cssSelector("p.alert"));
        Assert.assertTrue(errorMessages.size() > 0);
        System.out.println("Login failed, error message present");

        // Logged-in UI is not present
        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("span.team_menu_user_name"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getText().equals("Konstantin"));
        System.out.println("Login failed, username not present");
    }

    @Test()
    public static void loginSuccess() throws InterruptedException {
        login(true);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("span#team_menu_user_name"));
//        List<WebElement> buttonProfile =
//        new FluentWait<>(browser)
//                .withTimeout(Duration.ofSeconds(7))
//                .pollingEvery(Duration.ofMillis(500))
//                .ignoring(InvalidElementStateException.class)
//                .until(browser -> browser.findElements(By.cssSelector("span#team_menu_user_name")));

        Assert.assertTrue(
                buttonProfile.size() > 0 && buttonProfile.get(0).getText().equals("Konstantin"));
        System.out.println("Login Success");
    }


}
