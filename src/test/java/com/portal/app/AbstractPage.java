package com.portal.app;

import com.portal.app.webdriver.DataContainer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AbstractPage extends AbstractTest {
    private static Logger log = LogManager.getLogger(AbstractPage.class.getName());

    public static WebDriver driver;

    public AbstractPage() {
        driver = DataContainer.getDriver();
    }

    // NAVIGATION METHODS

    protected void goTo(String url) {
        log.info("Open " + url);
        driver.get(url);
        waitForPageLoaded(driver, 60);
    }

    protected void waitForPageLoaded(WebDriver driver, int timeout) {
        log.info("Wait " + timeout + " seconds for loading page");
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };

        Wait<WebDriver> wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(expectation);
        } catch (Throwable error) {
            log.error("Timeout waiting for page to load");
        }
    }

}

