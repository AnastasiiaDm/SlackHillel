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
        System.out.println("   enterURL test start");

        browser.findElement(By.cssSelector("a[data-clog-ui-element='link_sign_in_nav']")).click();
//        h.findAndFill(By.cssSelector("input#domain"), TestData.userURL + "\n");
        h.findAndFill(By.cssSelector("input#domain"), (isSuccess ? TestData.userURL : TestData.badURL) + "\n");
    }
    @Test(priority = -1)
    public static void enterURLFail() {
        enterURL(false);
        System.out.println("   enterURLFail test start");


        // Some error message is present
        List<WebElement> errorMessages = browser.findElements(By.cssSelector("p.alert"));
        Assert.assertTrue(errorMessages.size() > 0);
        System.out.println("enterURLFail, error message present");

        // Logged-in UI is not present
        List<WebElement> buttonEnterEmailAndPass = browser.findElements(By.cssSelector("p.browser_password"));
        Assert.assertFalse(
                buttonEnterEmailAndPass.size() > 0 );
        System.out.println("enterURLFail, username not present");
    }

    @Test(priority = -1)
    public static void enterURLSuccess() {
        enterURL(true);
        System.out.println("   enterURLSuccess test start");


        List<WebElement> buttonEnterEmailAndPass = browser.findElements(By.cssSelector("p.browser_password"));
//
        Assert.assertTrue(buttonEnterEmailAndPass.size() > 0 );
        System.out.println("enterURLSuccess Success");
    }

    private static void login(boolean isSuccess)  {

        h.findAndFill(By.cssSelector("input#email"), TestData.username);
        h.findAndFill(By.cssSelector("input#password"), (isSuccess ? TestData.pass : TestData.badPass) + "\n");
    }

    @Test(priority = -1)
    public static void loginFail() {
        login(false);
        System.out.println("   loginFail test start");


        // Some error message is present
        List<WebElement> errorMessages = browser.findElements(By.cssSelector("p.alert"));
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
    public static void loginSuccess() throws InterruptedException {
        login(true);
        System.out.println("   loginSuccess test start");

        Thread.sleep(8000);
        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("span#team_menu_user_name"));
//
//        List<WebElement> buttonProfile  = new FluentWait<>(browser)
//                .withTimeout(Duration.ofSeconds(7))
//                .pollingEvery(Duration.ofMillis(500))
//                .ignoring(InvalidElementStateException.class)
//                .until(browser -> browser.findElements(By.cssSelector("span#team_menu_user_name")));

        Assert.assertTrue(buttonProfile.size() > 0 && buttonProfile.get(0).getText().equals("Konstantin"));
        System.out.println("Login Success");
    }

    @Test(dependsOnMethods = {"loginSuccess"})
    public static void sendMessage() {
        System.out.println("   sendMessage test start");
        h.findAndFill(By.cssSelector("div.ql-editor"), TestData.newMessage);
        System.out.println("enter text success");

//        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a[class='issue-created-key issue-link']"));
//
//        Assert.assertTrue(linkNewIssues.size() != 0);
//
//        newIssuePath = linkNewIssues.get(0).getAttribute("href");
//
//        System.out.println("New issue Summary: " + newIssueSummary + "\n" + "createIssue success");
    }


}
