import org.openqa.selenium.By;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SlackTest extends TestBase {
    private static void enterURL(boolean isSuccess){
        browser.get(TestData.baseURL);

        browser.findElement(By.cssSelector("a[data-clog-ui-element='link_sign_in_nav']")).click();
//        h.findAndFill(By.cssSelector("input#domain"), TestData.userURL + "\n");
        h.findAndFill(By.cssSelector("input#domain"), (isSuccess ? TestData.userURL : TestData.badURL) + "\n");
    }
    @Test()
    public static void enterURLFail() {
        enterURL(false);

        // Some error message is present
        List<WebElement> errorMessages =
                browser.findElements
                        (By.cssSelector("p.alert"));
        Assert.assertTrue(errorMessages.size() > 0);
        System.out.println("enterURLFail, error message present");

        // Logged-in UI is not present
        List<WebElement> buttonEnterEmailAndPass = browser.findElements(By.cssSelector("p.browser_password"));
        Assert.assertFalse(
                buttonEnterEmailAndPass.size() > 0 );
        System.out.println("enterURLFail, username not present");
    }

    @Test()
    public static void enterURLSuccess() {
        enterURL(true);

        List<WebElement> buttonEnterEmailAndPass = browser.findElements(By.cssSelector("p.browser_password"));
//
        Assert.assertTrue(
                buttonEnterEmailAndPass.size() > 0 );
        System.out.println("enterURLSuccess Success");
    }

    private static void login(boolean isSuccess)  {

        h.findAndFill(By.cssSelector("input#email"), TestData.username);
        h.findAndFill(By.cssSelector("input#password"), (isSuccess ? TestData.pass : TestData.badPass) + "\n");
    }

    @Test()
    public static void loginFail() {
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
        browser.findElement(By.cssSelector("input#email")).clear();
    }

    @Test()
    public static void loginSuccess() {
        login(true);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("span#team_menu_user_name"));

        Assert.assertTrue(
                buttonProfile.size() > 0 && buttonProfile.get(0).getText().equals("Konstantin"));
        System.out.println("Login Success");
    }
}
