import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;
import java.util.List;

public class ShortLogin extends TestBase {
    static String messagePath;

    @Test
    private static void login() {

        browser.get(TestData.baseURL);
        System.out.println("   enterURL test start");

        browser.findElement(By.cssSelector("a[data-clog-ui-element='link_sign_in_nav']")).click();
        h.findAndFill(By.cssSelector("input#domain"), TestData.userURL + "\n");

        h.findAndFill(By.cssSelector("input#email"), TestData.username);
        h.findAndFill(By.cssSelector("input#password"), TestData.pass + "\n");
    }

    @Test(dependsOnMethods = {"login"})
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
    public static void messageReceive( List<WebElement> sentMessageText) {
        System.out.println("messageReceive start");
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

//        Assert.assertEquals(receivedMessageText.get(0).getText(), sentMessageText.get(0).getText());
//        System.out.println("messages match");

//        mistake:
//        [Utils] [ERROR] [Error] org.testng.TestNGException:
//        Cannot inject @Test annotated Method [messageReceive] with [interface java.util.List].
//        For more information on native dependency injection please refer to http://testng.org/doc/documentation-main.html#native-dependency-injection
//        at org.testng.internal.Parameters.checkParameterTypes(Parameters.java:407)
//        at org.testng.internal.Parameters.createParametersForMethod(Parameters.java:356)
//        at org.testng.internal.Parameters.createParameters(Parameters.java:635)
//        at org.testng.internal.Parameters.handleParameters(Parameters.java:769)
//        at org.testng.internal.ParameterHandler.handleParameters(ParameterHandler.java:49)
//        at org.testng.internal.ParameterHandler.createParameters(ParameterHandler.java:37)
//        at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:924)
//        at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:125)
//        at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:109)
//        at org.testng.TestRunner.privateRun(TestRunner.java:648)
//        at org.testng.TestRunner.run(TestRunner.java:505)
//        at org.testng.SuiteRunner.runTest(SuiteRunner.java:455)
//        at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:450)
//        at org.testng.SuiteRunner.privateRun(SuiteRunner.java:415)
//        at org.testng.SuiteRunner.run(SuiteRunner.java:364)
//        at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
//        at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:84)
//        at org.testng.TestNG.runSuitesSequentially(TestNG.java:1208)
//        at org.testng.TestNG.runSuitesLocally(TestNG.java:1137)
//        at org.testng.TestNG.runSuites(TestNG.java:1049)
//        at org.testng.TestNG.run(TestNG.java:1017)
//        at org.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:72)
//        at org.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:123)
//

//        org.testng.TestNGException:
//        Cannot inject @Test annotated Method [messageReceive] with [interface java.util.List].
//        For more information on native dependency injection please refer to http://testng.org/doc/documentation-main.html#native-dependency-injection
//
//        at org.testng.internal.Parameters.checkParameterTypes(Parameters.java:407)
//        at org.testng.internal.Parameters.createParametersForMethod(Parameters.java:356)
//        at org.testng.internal.Parameters.createParameters(Parameters.java:635)
//        at org.testng.internal.Parameters.handleParameters(Parameters.java:769)
//        at org.testng.internal.ParameterHandler.handleParameters(ParameterHandler.java:49)
//        at org.testng.internal.ParameterHandler.createParameters(ParameterHandler.java:37)
//        at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:924)
//        at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:125)
//        at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:109)
//        at org.testng.TestRunner.privateRun(TestRunner.java:648)
//        at org.testng.TestRunner.run(TestRunner.java:505)
//        at org.testng.SuiteRunner.runTest(SuiteRunner.java:455)
//        at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:450)
//        at org.testng.SuiteRunner.privateRun(SuiteRunner.java:415)
//        at org.testng.SuiteRunner.run(SuiteRunner.java:364)
//        at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
//        at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:84)
//        at org.testng.TestNG.runSuitesSequentially(TestNG.java:1208)
//        at org.testng.TestNG.runSuitesLocally(TestNG.java:1137)
//        at org.testng.TestNG.runSuites(TestNG.java:1049)
//        at org.testng.TestNG.run(TestNG.java:1017)
//        at org.testng.IDEARemoteTestNG.run(IDEARemoteTestNG.java:72)
//        at org.testng.RemoteTestNGStarter.main(RemoteTestNGStarter.java:123)


    }


}
