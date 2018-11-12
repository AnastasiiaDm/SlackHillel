package Slack;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class SlackActions{
    static String messagePath;
    private WebDriver browser;
    private Helper h;

    @FindBy(css = "a[data-clog-ui-element='link_sign_in_nav']")
    WebElement signInButton;

    @FindBy(css = "input#domain")
    WebElement URLInput;

    @FindBy(css = "p.alert")
    List<WebElement> errorMessages;

    @FindBy(css = "p.browser_password")
    List<WebElement> buttonEnterPass;

    @FindBy(css = "input#email")
    WebElement emailInput;

    @FindBy(css = "input#password")
    WebElement passInput;

    @FindBy(css = "span.team_menu_user_name")
    List<WebElement> userAva;

    @FindBy(xpath = "//*[text() = 'Nasik']")
    WebElement sendToUser;

//    @FindBy(xpath = "//*[text()='" + messagePath + "']")
//    WebElement sentMessageText;


    @FindBy(css = "div#team_menu")
    WebElement menuButton;

    @FindBy(css = "a.menu_list_link")
    WebElement singoutButton;

    @FindBy(css = "a.btn")
    WebElement signBackInButton;


    public SlackActions (WebDriver browser) {
        this.browser = browser;
        this.h = new Helper(browser);
    }

    private void enterURL(boolean isSuccess) {
        browser.get(TestData.baseURL);
        System.out.println("   enterURL test start");
        signInButton.click();

        h.fill(URLInput, (isSuccess ? TestData.userURL : TestData.badURL) + "\n");
    }

    public void enterURLFailCheck() {
        enterURL(false);
        System.out.println("   enterURLFail test start");

        // Some error message is present
        Assert.assertTrue(errorMessages.size() > 0);
        System.out.println("enterURLFail, error message present");

        // Logged-in UI is not present
        Assert.assertFalse(buttonEnterPass.size() > 0);
        System.out.println("enterURLFail, username not present");
    }

    public void enterURLSuccessCheck() {
        enterURL(true);
        System.out.println("   enterURLSuccess test start");

        Assert.assertTrue(buttonEnterPass.size() > 0);
        System.out.println("enterURLSuccess Success");
    }

    private void login(boolean isSuccess) {

        h.fill(emailInput, TestData.username);
        h.fill(passInput, (isSuccess ? TestData.pass : TestData.badPass) + "\n");
    }
    public  void loginFailCheck() {
        login(false);
        System.out.println("   loginFail test start");

        // Some error message is present
        Assert.assertTrue(errorMessages.size() > 0);
        System.out.println("Login failed, error message present");

        // Logged-in UI is not present
        Assert.assertFalse(
                userAva.size() > 0 && userAva.get(0).getText().equals(TestData.senderName));
        System.out.println("Login failed, username not present");
        emailInput.clear();
    }

    public void loginSuccessCheck() {
        login(true);
        System.out.println("   loginSuccess test start");

        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(800))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotVisibleException.class)
                .ignoring(NoSuchElementException.class)
                .until(browser -> userAva);
        System.out.println(userAva.size());
        Assert.assertTrue(userAva.size() > 0 && userAva.get(0).getText().equals(TestData.senderName));
        System.out.println("Login Success");
    }

    public void sendMessageCheck() {
        System.out.println("   sendMessage test start");

        sendToUser.click();

        messagePath = TestData.newMessage;

        h.fill(sendToUser, messagePath + "\n");
        System.out.println("enter text success");

        List<WebElement> sentMessageText = browser.findElements(By.xpath("//*[text()='" + messagePath + "']"));

        System.out.println("Sent message: " + sentMessageText.get(0).getText());
        Assert.assertTrue(sentMessageText.size() > 0);
    }

    public void logout() {
//        browser.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
//        ArrayList<String> tabs = new ArrayList<String>(browser.getWindowHandles());
//        browser.switchTo().window(tabs.get(1));
//        browser.get(Slack.TestData.baseURL);

        menuButton.click();

        singoutButton.click();

        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(800))
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotVisibleException.class)
                .until(browser -> signBackInButton).click();
        }

        public void loginReceiver(){

        h.fill(emailInput, TestData.assignerName);
        h.fill(passInput, TestData.assignerPass + "\n");

        browser.findElement(By.xpath("//*[text() = 'Konstantin']")).click();

        List<WebElement> receivedMessageText = browser.findElements(By.xpath("//*[text()='" + messagePath + "']"));

        System.out.println("Received message: " + receivedMessageText.get(0).getText());
        Assert.assertTrue(receivedMessageText.size() > 0);
    }
}
