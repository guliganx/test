package com.portal.app.Exercise.Tests;

import com.portal.app.AbstractTest;
import com.portal.app.Exercise.Pages.PageObject;
import com.portal.app.Exercise.Pages.coral;
import com.portal.app.RetryAnalyzerImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import spring.User;

public class testcase1 extends AbstractTest {
    private Logger log = LogManager.getLogger(testcase1.class.getName());

    @Autowired
    @Qualifier("testBean")
    private User user;

    //Application Context device=chrome ; platform=desktop

    @Test(retryAnalyzer=RetryAnalyzerImpl.class)
    public void testCase1() {
        coral co = new coral();
        co.openPage();
        co.splashClose();
        Assert.assertFalse(co.isUserLoggedInDesktop(), "The user was already logged in.");
        co.clickSportFromLeftNav("Football");
        co.clickFirstAvailableSelection();

    }
}
