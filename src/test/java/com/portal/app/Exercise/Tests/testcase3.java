package com.portal.app.Exercise.Tests;

import com.portal.app.AbstractTest;
import com.portal.app.Exercise.Pages.betcoral;
import com.portal.app.RetryAnalyzerImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import spring.User;

public class testcase3 extends AbstractTest {
    private Logger log = LogManager.getLogger(testcase3.class.getName());

    @Autowired
    @Qualifier("testBean")
    private User user;

    //Application Context device=iPhone 6 ; platform=iOS

    @Test(retryAnalyzer=RetryAnalyzerImpl.class)
    public void testCase2() {
        betcoral bc = new betcoral();
        bc.openPage();
        bc.isUserLoggedInOnMobile();
        bc.userLogin();
        Assert.assertFalse(bc.isUserLoggedInOnMobile(), "The user was already logged in.");

    }


}
