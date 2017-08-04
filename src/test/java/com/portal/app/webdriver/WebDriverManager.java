package com.portal.app.webdriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;

/**
 * Class created only to use events listeners
 */
public class WebDriverManager extends EventFiringWebDriver {

    public WebDriverManager(String device, String orientation, String deviceType, String launch, String platform,String scope,String nativeProduct) throws MalformedURLException {
        super(new WebdriverFactory().createDriver(device, orientation, deviceType, launch, platform, scope,nativeProduct));
    }
}