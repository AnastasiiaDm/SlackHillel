import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class SlackTest extends TestBase {
    static String messagePath;

    private static void enterURL(boolean isSuccess) {
        browser.get(TestData.baseURL);
        System.out.println("   enterURL test start");
        browser.findElement(By.cssSelector("a[data-clog-ui-element='link_sign_in_nav']")).click();

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
                buttonEnterEmailAndPass.size() > 0);
        System.out.println("enterURLFail, username not present");
    }

    @Test(priority = 1)
    public static void enterURLSuccess() {
        enterURL(true);
        System.out.println("   enterURLSuccess test start");

        List<WebElement> buttonEnterEmailAndPass = browser.findElements(By.cssSelector("p.browser_password"));
//
        Assert.assertTrue(buttonEnterEmailAndPass.size() > 0);
        System.out.println("enterURLSuccess Success");
    }

    private static void login(boolean isSuccess) {

        h.findAndFill(By.cssSelector("input#email"), TestData.username);
        h.findAndFill(By.cssSelector("input#password"), (isSuccess ? TestData.pass : TestData.badPass) + "\n");
    }

    @Test(dependsOnMethods = {"enterURLSuccess"}, priority = -1)
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

    @Test(dependsOnMethods = {"enterURLSuccess"}, priority = 1)
    public static void loginSuccess() {
        login(true);
        System.out.println("   loginSuccess test start");

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("span#team_menu_user_name"));

        Assert.assertTrue(buttonProfile.size() > 0 && buttonProfile.get(0).getText().equals("Konstantin"));
        System.out.println("Login Success");
    }

    @Test(dependsOnMethods = {"loginSuccess"})
    public static void sendMessage() {
        System.out.println("   sendMessage test start");

        browser.findElement(By.xpath("//*[text() = 'Nasik']")).click();

        messagePath = TestData.newMessage;

        h.findAndFill(By.cssSelector("div#msg_input div.ql-editor"), messagePath + "\n");
        System.out.println("enter text success");

        List<WebElement> sentMessageText = browser.findElements(By.xpath("//*[text()='" + messagePath + "']"));

        System.out.println("Sent message: " + sentMessageText.get(0).getText());
        Assert.assertTrue(sentMessageText.size() > 0);
    }

    @Test(dependsOnMethods = {"sendMessage"})
    public static void messageReceive() {
//        browser.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
//        ArrayList<String> tabs = new ArrayList<String>(browser.getWindowHandles());
//        browser.switchTo().window(tabs.get(1));
//        browser.get(TestData.baseURL);

        browser.findElement(By.cssSelector("div#team_menu")).click();

        browser.findElement(By.cssSelector("a.menu_list_link")).click();

        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(800))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotVisibleException.class)
                .until(browser -> browser.findElement(By.cssSelector("a.btn"))).click();

        h.findAndFill(By.cssSelector("input#email"), TestData.assignerName);
        h.findAndFill(By.cssSelector("input#password"), TestData.assignerPass + "\n");

        browser.findElement(By.xpath("//*[text() = 'Konstantin']")).click();

        List<WebElement> receivedMessageText = browser.findElements(By.xpath("//*[text()='" + messagePath + "']"));

        System.out.println("Received message: " + receivedMessageText.get(0).getText());
        Assert.assertTrue(receivedMessageText.size() > 0);
    }


}
