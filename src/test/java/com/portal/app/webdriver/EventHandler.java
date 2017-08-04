package com.portal.app.webdriver;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public abstract class EventHandler implements WebDriverEventListener {
    private static Logger log = LogManager.getLogger(EventHandler.class.getName());

    public void beforeNavigateTo(java.lang.String s, WebDriver webDriver) {}
    public void afterNavigateTo(java.lang.String s, WebDriver webDriver) {}
    public void beforeNavigateBack(WebDriver webDriver) {}
    public void afterNavigateBack(WebDriver webDriver) {}
    public void beforeNavigateForward(WebDriver webDriver) {}
    public void afterNavigateForward(WebDriver webDriver) {}
    public void beforeNavigateRefresh(WebDriver webDriver) {}
    public void afterNavigateRefresh(WebDriver webDriver) {}
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {}
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {}
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {}
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {}
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver) {}
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver) {}
    public void beforeScript(java.lang.String s, WebDriver webDriver) {}
    public void afterScript(java.lang.String s, WebDriver webDriver) {}
    public void onException(java.lang.Throwable throwable, WebDriver webDriver) {}
}
