package com.portal.app.Exercise.Pages;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import com.portal.app.AbstractPage;
import com.portal.app.Exercise.model.Selection;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class betcoral extends AbstractPage {
    //HomePage
    @FindBy(css = "#logo")
    private WebElement SITE_LOGO;
    @FindBy(css = "#header .button.btn-primary")
    private WebElement HEADER_LOGIN_BUTTON_MOBILE;
    @FindBy(css = "#header .button.btn-secondary")
    private WebElement HEADER_JOIN_BUTTON_MOBILE;
    @FindBy(css = ".odds-card")
    private WebElement ODDS_CARD;


    private Logger log = LogManager.getLogger(betcoral.class.getName());

    private final String logedInCookieName = "pas[galabingo][real][isOnline]";

    WebDriverWait wait = new WebDriverWait(driver,30);

    public betcoral() {
        PageFactory.initElements(driver, this);
    }

    public void openPage() {
        super.goTo("https://bet.coral.co.uk/#/");
        waitForPageLoaded(driver, 30);
    }

    public Boolean isCoral(){
        log.info("Checking if Login and Join Buttons are present");
        try{
            wait.until(ExpectedConditions.visibilityOf(HEADER_LOGIN_BUTTON_MOBILE));
            Assert.assertTrue(HEADER_JOIN_BUTTON_MOBILE.isDisplayed(),"Login Button is there but Join Button not");
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

    public boolean isUserLoggedInOnMobile() {
        return !HEADER_LOGIN_BUTTON_MOBILE.isDisplayed();
    }

    public void userLogin () {

        this.navigateToElementById("login_username");
        this.clear();
        this.assertTrue(this.typeIn(new CharSequence[]{username}));

    }

    public void closeTutorial() {
        driver.findElement(By.cssSelector("button[ng-click='hideTutorial()']")).click();
    }

    public void clickSportFromCarousel() {
        driver.findElement(By.cssSelector("[data-uat='mainNav'] [href='#/football']")).click();
    }

    public String getSelectedTab () {
        return driver.findElement(By.cssSelector("#contents .tabs-panel .selected")).getText();
    }


   public Selection clickFirstAvailableSelection() {
       String selectionName = ODDS_CARD.findElement(By.cssSelector(".odds-names")).getText();
       String selectionPrice = ODDS_CARD.findElement(By.cssSelector(".btn-bet span[data-crlat='oddsPrice']")).getText();

       Selection selection = new Selection();
       selection.setName(selectionName);
       selection.setPrice(selectionPrice);

       ODDS_CARD.findElement(By.cssSelector(".btn-bet")).click();
       return selection;
        //driver.findElement(By.cssSelector("div.bet-price:not(.suspended)")).click();
    }


}