package Slack;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SlackTest extends TestBase {
    static SlackActions steps;

    @BeforeTest
    public static void init() {
        steps = PageFactory.initElements(browser, SlackActions.class);
    }

    @Test(priority = -1)
    public static void enterURLFail() {
        steps.enterURLFailCheck();
    }

    @Test(priority = 1)
    public static void enterURLSuccess() {
       steps.enterURLSuccessCheck();
    }

    @Test(dependsOnMethods = {"enterURLSuccess"}, priority = -1)
    public static void loginFail() {
       steps.loginFailCheck();
    }

    @Test(dependsOnMethods = {"enterURLSuccess"}, priority = 1)
    public static void loginSuccess() {
        steps.loginSuccessCheck();
    }

    @Test(dependsOnMethods = {"loginSuccess"})
    public static void sendMessage() {
        steps.sendMessageCheck();
    }

//    @Test(dependsOnMethods = {"sendMessage"})
//    public static void messageReceive() {
//
//    }


}
