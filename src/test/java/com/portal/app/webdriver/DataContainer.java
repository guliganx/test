package com.portal.app.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DataContainer {
    private static WebDriverManager driver;

    public static void setDriver(WebDriverManager driver) {
        DataContainer.driver = driver;
        // add listeners to the driver
        EventHandler handler=new EventHandler() {
            @Override
            public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

            }

            @Override
            public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

            }
        };

        DataContainer.driver.register(handler);
    }

    public static WebDriverManager getDriver() {
        return driver;
    }


}

