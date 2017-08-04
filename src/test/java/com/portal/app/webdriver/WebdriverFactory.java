package com.portal.app.webdriver;

import atu.testng.reports.ATUReports;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class WebdriverFactory {
    private Logger log = LogManager.getLogger(WebdriverFactory.class.getName());

    ////////USER AGENTS////////////
    private final String NEXUS5_USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36";
    private final String GALAXY_S6_USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Galaxy S6 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36";
    private final String GALAXY_S5_USER_AGENT = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36";
    private final String IPHONE_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
    private final String IPAD_USER_AGENT = "Mozilla/5.0 (iPad; CPU OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1";
    private final String DESKTOP_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) Daniel AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
    //////////////////////////////
    private final String CHROME_DRIVER_PATH = "Drivers/chromedriver.exe";
    private final String EPOS2_DRIVER_PATH = "/Jenkins/coralEPOS/Coral EPOS 2.exe";
    private final String GECKO_DRIVER_PATH = "Drivers/geckodriver.exe";

    private final long SCRIPT_TIMEOUT = 30;
    private final long IMPLICITLY_TIMEOUT = 5;
    private final long PAGE_LOAD_TIMEOUT = 120;

    private int width, height;
    private double pixelRatio;
    private String UAS = "", deviceCap = "";

    public static String Platform;
    public static Boolean pureNative=false;

    public static WebDriver driver;

    public WebDriver createDriver(String driverName, String orientation, String deviceType, String launch, String platform, String Scope, String nativeProduct) throws MalformedURLException {

        if (driverName.equalsIgnoreCase("chrome")) {
                log.info("Create ChromeDriver");
                DesiredCapabilities caps = new DesiredCapabilities();

                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
                System.setProperty("browserWidth", "1280"); //Set width bigger than mobile is enough
                System.setProperty("device", "chrome");

                Map<String, Object> chromeOptions = new HashMap<String, Object>();
                caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                caps.setBrowserName("chrome");
                driver = new ChromeDriver(caps);

            }else if(driverName.equalsIgnoreCase("edge")){
                System.setProperty("webdriver.edge.driver", "Drivers/MicrosoftWebDriver.exe");
                DesiredCapabilities caps = DesiredCapabilities.edge();
                driver = new EdgeDriver(caps);
            }else if (driverName.equalsIgnoreCase("firefox")) {
                log.info("Create FirefoxDriver");
                System.setProperty("webdriver.gecko.driver", GECKO_DRIVER_PATH);
                System.setProperty("browserWidth", "1280");
                System.setProperty("device", "firefox");

                System.setProperty("device", "desktop");
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();

                capabilities.setCapability("marionette", true);
                //capabilities.setCapability("firefox_binary", "C:\\Users\\daniel.pino\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
                driver = new FirefoxDriver(capabilities);

            }else {
                log.info("Create Driver for [" + driverName + "][" + orientation + "]");
                switch (driverName) {
                    case "Google Nexus 5":
                        createLocalDriver(NEXUS5_USER_AGENT, orientation, 412, 732, 2.8, driverName);
                        break;
                    case "Samsung S6":
                        createLocalDriver(GALAXY_S6_USER_AGENT, orientation, 360, 640, 2.8, driverName);
                        break;
                    case "Samsung S5":
                        createLocalDriver(GALAXY_S5_USER_AGENT, orientation, 360, 640, 2.8, driverName);
                        break;
                    case "iPhone 6":
                        createLocalDriver(IPHONE_USER_AGENT, orientation, 375, 667, 2.8, driverName);
                        break;
                    case "iPhone 6 Plus":
                        createLocalDriver(IPHONE_USER_AGENT, orientation, 414, 736, 2.8, driverName);
                        break;
                    case "iPhone 5s":
                        createLocalDriver(IPHONE_USER_AGENT, orientation, 320, 568, 2.8, driverName);
                        break;
                    case "iPad Air 2":
                        createLocalDriver(IPAD_USER_AGENT, orientation, 768, 1024, 2.1, driverName);
                        break;
                }
                driver = createLocalWebDriver();
            }

            System.setProperty("Driver", driverName);

            if (!deviceType.toLowerCase().contains("real")) {
                log.info("Set timeouts");
                driver.manage().timeouts().setScriptTimeout(SCRIPT_TIMEOUT, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
                driver.manage().timeouts().implicitlyWait(IMPLICITLY_TIMEOUT, TimeUnit.SECONDS);


                log.info("Maximize window");
                driver.manage().window().maximize();
            } else {
                log.info("Set implicitly timeout");
                driver.manage().timeouts().implicitlyWait(IMPLICITLY_TIMEOUT, TimeUnit.SECONDS);
            }
            ATUReports.setWebDriver(driver);
            return driver;
        }

    private void createLocalDriver(String UAS, String orientation, int width, int height, double pixelRatio, String deviceCap) {
        this.UAS = UAS;
        if (orientation.equalsIgnoreCase("landscape")){
            this.width = height;
            this.height = width;
        } else {
            this.width = width;
            this.height = height;
        }
        this.pixelRatio = pixelRatio;
        this.deviceCap = deviceCap;
    }

    private WebDriver createLocalWebDriver() {

        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        System.setProperty("browserWidth", String.valueOf(this.width));
        System.setProperty("browserHeight", String.valueOf(this.height));
        System.setProperty("device", this.deviceCap);

        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", this.width);
        deviceMetrics.put("height", this.height);
        deviceMetrics.put("pixelRatio", this.pixelRatio);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", this.UAS);

        Map<String, Object> chromeOptions = new HashMap<String, Object>();
        chromeOptions.put("mobileEmulation", mobileEmulation);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        capabilities.setCapability(CapabilityType.ROTATABLE, true);
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                org.openqa.selenium.UnexpectedAlertBehaviour.ACCEPT);

        return new ChromeDriver(capabilities);
    }

    public static String getPlatform() {
        return Platform;
    }
    public void setPlatform(String Platform) {
        this.Platform = Platform;
    }
}