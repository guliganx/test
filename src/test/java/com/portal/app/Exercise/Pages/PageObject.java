package com.portal.app.Exercise.Pages;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import com.portal.app.AbstractPage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PageObject extends AbstractPage {
    //HomePage
    @FindBy(css = "#logo")
    private WebElement SITE_LOGO;
    @FindBy(css = "#top-login-form .login-button")
    private WebElement HEADER_LOGIN_BUTTON;
    @FindBy(css = "#header .button btn-primary")
    private WebElement HEADER_LOGIN_BUTTON_MOBILE;
    @FindBy(css = "#top-login-form .join-button")
    private WebElement HEADER_JOIN_BUTTON;
    @FindBy(css = "div.bet-price:not(.suspended)")
    private WebElement ACTIVE_SELECTION;


    private Logger log = LogManager.getLogger(com.portal.app.Exercise.Pages.PageObject.class.getName());

    private final String logedInCookieName = "pas[galabingo][real][isOnline]";

    WebDriverWait wait = new WebDriverWait(driver,30);

    public PageObject() {
        PageFactory.initElements(driver, this);
    }

    public void openPage() {
        super.goTo("http://www.coral.co.uk");
        waitForPageLoaded(driver, 30);
    }

    public Boolean isCoral(){
        log.info("Checking if Login and Join Buttons are present");
        try{
            wait.until(ExpectedConditions.visibilityOf(HEADER_LOGIN_BUTTON));
            Assert.assertTrue(HEADER_JOIN_BUTTON.isDisplayed(),"Login Button is there but Join Button not");
            ATUReports.add("LOGIN AND JOIN HEADER BUTTONS ARE PRESENT", LogAs.PASSED,null);
            return true;
        }catch (Exception ex){
            log.info(ex);
            ATUReports.add("LOGIN AND JOIN HEADER BUTTONS ARE NOT PRESENT", LogAs.FAILED,new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));

            return false;

        }
    }

    public void splashClose(){
        log.info("Closing Splash Page");
        SITE_LOGO.click();
        ATUReports.add("SPLASH PAGE LOGO CLICKED", LogAs.PASSED,null);
    }

    public boolean isUserLoggedInDesktop() {
       return !HEADER_LOGIN_BUTTON.isDisplayed();
    }

    public boolean isUserLoggedInOnMobile() {
        return !HEADER_LOGIN_BUTTON_MOBILE.isDisplayed();
    }

    public void clickSportFromLeftNav(String sportName) {
        driver.findElement(By.linkText(sportName)).click();
    }

    public void clickFirstAvailableSelection() {
        ACTIVE_SELECTION.click();
        //driver.findElement(By.cssSelector("div.bet-price:not(.suspended)")).click();
    }
//    public void clickSportFromCarousel
//            (".scroll-container a[title='Football']")

    public void openBetslip (String Betslip){
        driver.findElement(By.className("bs-tooltip-trigger")).click();
    }

}