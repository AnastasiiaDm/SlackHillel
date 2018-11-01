import org.openqa.selenium.By;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ShortLogin extends TestBase {
    static String messagePath;

    @Test
    private static void login()  {

        browser.get(TestData.baseURL);
        System.out.println("   enterURL test start");

        browser.findElement(By.cssSelector("a[data-clog-ui-element='link_sign_in_nav']")).click();
        h.findAndFill(By.cssSelector("input#domain"), TestData.userURL + "\n");

        h.findAndFill(By.cssSelector("input#email"), TestData.username);
        h.findAndFill(By.cssSelector("input#password"),  TestData.pass + "\n");
    }


    @Test(dependsOnMethods = {"login"})
    public static void sendMessage() throws InterruptedException {
        System.out.println("   sendMessage test start");

        browser.findElement(By.xpath("//*[text() = 'Nasik']")).click();

        messagePath = TestData.newMessage;

        h.findAndFill(By.cssSelector("div#msg_input div.ql-editor"), messagePath + "\n");
        System.out.println("enter text success");

        List<WebElement> messageText = browser.findElements(By.xpath("//*[text()='"+ messagePath + "']"));

        Thread.sleep(5000);
        System.out.println(messageText.get(0));
        Assert.assertTrue(messageText.size() > 0);

//        WebElement messageText = browser.findElement(By.cssSelector("span.c-message__body"));
//        messageText.getText();
//
//        messagePath = messageText.getAttribute("span");
//        System.out.println(messageText.getText() );
//
//        System.out.println("New issue Summary: " + newIssueSummary + "\n" + "createIssue success");
    }


}
