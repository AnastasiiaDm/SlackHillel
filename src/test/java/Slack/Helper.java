package Slack;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    private WebDriver browser;

    Helper(WebDriver currentBrowser) {
        browser = currentBrowser;
    }

    //    public WebElement findAndFill(By selector, String value) {
//
//        WebElement elem = browser.findElement(selector);
//        elem.sendKeys(value);
//        return elem;
//    }
    public WebElement fill(WebElement selector, String value) {

        selector.sendKeys(value);
        return selector;
    }

    public static String timeStamp() {
        return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    }
}

