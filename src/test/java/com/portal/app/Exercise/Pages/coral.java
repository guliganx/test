package com.portal.app.Exercise.Pages;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import com.portal.app.AbstractPage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class coral extends AbstractPage {
    //HomePage
    @FindBy(css = "#logo")
    private WebElement SITE_LOGO;
    @FindBy(css = "#top-login-form .login-button")
    private WebElement HEADER_LOGIN_BUTTON;
     @FindBy(css = "#top-login-form .join-button")
    private WebElement HEADER_JOIN_BUTTON;
    @FindBy(css = ".matches .match.featured-match:first-child .bet-title")
    private WebElement SELECTION_TITLE;
    @FindBy(css = ".matches .match.featured-match:first-child .home-odds>div>.odds-fractional")
    private WebElement SELECTION_ODD;
    @FindBy(css = "#betslip-content .bs-event")
    private WebElement BETSLIP_SELECTION_TITLE;


    private final String LOGOUT_POPUP_TITLE_FIRST = "Logout";
    private final String LOGOUT_POPUP_MESSAGE_FIRST = "Are you sure you want to logout?";
    private final String LOGOUT_POPUP_TITLE_SECOND = "Signed Out";
    private final String LOGOUT_POPUP_MESSAGE_SECOND = "You have been logged out. Please log in again.";


    private Logger log = LogManager.getLogger(coral.class.getName());

    private final String logedInCookieName = "pas[galabingo][real][isOnline]";

    WebDriverWait wait = new WebDriverWait(driver,30);

    public coral() {
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

    public void clickSportFromLeftNav(String sportName) {
        driver.findElement(By.linkText(sportName)).click();
    }

    public void clickFirstAvailableSelection() {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebDriverWait wait = new WebDriverWait(driver,20);
        //Store Selection Info
        String SelectionTitle=SELECTION_TITLE.getText();
        String SelectionOdd=SELECTION_ODD.getText();
        //Scroll to element using javascript executor
        js.executeScript("arguments[0].scrollIntoView();", SELECTION_ODD);
        js.executeScript("window.scrollBy(0, 100);");
        sleep(1000);
        //Add selection to betslip
        log.info("Selection==>"+SelectionTitle+"|||"+SelectionOdd);
        SELECTION_ODD.click();
        //Wait for betslip to be shown
        wait.until(ExpectedConditions.visibilityOf(BETSLIP_SELECTION_TITLE));
        //Assert Event in betslip is the correct one
        String bsSelection=BETSLIP_SELECTION_TITLE.getText();
        Assert.assertEquals(SelectionTitle,bsSelection,"Selection added is not the one in betslip");
        ATUReports.add("SELECTION "+SelectionTitle+ "ADDED CORRECTLY TO BETSLIP", LogAs.PASSED,null);
        //driver.findElement(By.cssSelector("div.bet-price:not(.suspended)")).click();
    }

}