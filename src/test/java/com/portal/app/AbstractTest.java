package com.portal.app;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.portal.app.webdriver.DataContainer;
import com.portal.app.webdriver.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

//import com.jcraft.jsch.ChannelExec;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.Session;

@ContextConfiguration(locations = {"/spring-config.xml"})
@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class,TestListener.class})
public class AbstractTest extends AbstractTestNGSpringContextTests {
    private static Logger log;

    protected static SoftAssert softAssert;

    private static final int MAX_MOBILE_WIDTH = 767;
    private static final int MAX_TABLET_WIDTH = 1280;
    private final String SCREENSHOT_DIRECTORY_PATH = "reports/screenshots/";
    private static final String REPORTS_DIRECTORY_PATH = "reports/Results/";
    public static String[] classScreenShots=new String[1000];
    public static int classScreenShotsCounter;
    public static boolean recordVideo=false;
    public static boolean closeIosAlertAndTutorialForNativeApps = false;

    // count clicks
    private static boolean countClicks = false; // set to start counting and unset to stop
    private static int clicks = 0; // number of clicks
    private static String lastElement = ""; // last clicked element (sometimes we have to click more than once to go forward but user needs click only once
    public static int iteration = 0, maxRetries = 1, currentTest = 0, totalPass = 0, totalFail = 0, totalSkip = 0;
    private int newDriver;

    //Create object of ATUTestRecorder
    //Provide path to store videos and file name format.
    static {
        System.setProperty("atu.reporter.config", "src/test/config/atu.properties");
    }

    @Autowired
    @Qualifier("driver")
    protected WebDriverManager driver;

    /* Method executed only once - at the beginning of suite */
    @BeforeSuite
    public void setVariables(){
        DOMConfigurator.configure("src/test/resources/log4j.xml"); // configure log4j
        log =  LogManager.getLogger(AbstractTest.class.getName());
        System.setProperty("Iteration", String.valueOf(iteration));
        System.setProperty("maxRetries", String.valueOf(maxRetries));
        System.setProperty("currentTest", String.valueOf(currentTest));
        System.setProperty("totalPass", String.valueOf(totalPass));
        System.setProperty("totalFails", String.valueOf(totalFail));
        System.setProperty("totalSkip", String.valueOf(totalSkip));

    }

    /* Method executed before each test class (in our case before each test) */
    @BeforeClass(alwaysRun = true)
    public void setUp(ITestContext context) throws Exception {
        log.info("Before class. Set up environment");
        // store driver and applicationDate to be avaiable for AbstractPage
        DataContainer.setDriver(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void createSoftAssert() {
        log.info("Starting here the " + this.getClass().getSimpleName() + " test");
        softAssert = new SoftAssert();
    }

    /* Method executed after each method */
    @AfterMethod(alwaysRun = true)
    public void checkForFailure(ITestResult result) {
        log.info("After method. Check for failure");
        iteration = Integer.parseInt(System.getProperty("Iteration"));
        maxRetries = Integer.parseInt(System.getProperty("maxRetries"));

        totalPass = Integer.parseInt(System.getProperty("totalPass"));
        totalFail = Integer.parseInt(System.getProperty("totalFails"));
        totalSkip = Integer.parseInt(System.getProperty("totalSkip"));

        log.info("Iteration==>" + iteration + "||Status==>" + result.getStatus() + "||Max Retries==>" + maxRetries);

        int status = result.getStatus();
        if (status == 1) {
            totalPass++;
            System.setProperty("totalPass", String.valueOf(totalPass));
        }
        if (status == 3) {
            totalSkip++;
            System.setProperty("totalSkip", String.valueOf(totalSkip));
        }

        if (status == 2) {
            if (iteration >= maxRetries) {
                totalFail++;
                System.setProperty("totalFails", String.valueOf(totalFail));
                iteration = 0;
            } else {
                iteration++;
            }
        }
    }

    /* Method executed after each class (test) */
    @AfterClass(alwaysRun = true)

    @AfterSuite(alwaysRun = true)
    protected void tearDown() throws IOException {
        log.info("After class. Tear down");
        totalFail = Integer.parseInt(System.getProperty("totalFails"));
        totalPass = Integer.parseInt(System.getProperty("totalPass"));
        totalSkip = Integer.parseInt(System.getProperty("totalSkip"));
        log.info("Total Passed==>" + totalPass + " || Total Failed on All Iterations==>" + totalFail+"||Total Skipped==>"+totalSkip+"||");
        driver.quit();
    }

    protected static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}