package com.portal.app.Exercise.Tests;

import com.portal.app.AbstractTest;
import com.portal.app.Exercise.Pages.PageObject;
import com.portal.app.RetryAnalyzerImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import spring.User;

public class Example extends AbstractTest {
    private Logger log = LogManager.getLogger(com.portal.app.Exercise.Tests.Example.class.getName());

    private final String LOGOUT_POPUP_TITLE_FIRST = "Logout";
    private final String LOGOUT_POPUP_MESSAGE_FIRST = "Are you sure you want to logout?";
    private final String LOGOUT_POPUP_TITLE_SECOND = "Signed Out";
    private final String LOGOUT_POPUP_MESSAGE_SECOND = "You have been logged out. Please log in again.";

    @Autowired
    @Qualifier("testBean")
    private User user;

    @Test(retryAnalyzer=RetryAnalyzerImpl.class)
    public void loginLogout() {

        PageObject po = new PageObject();
        po.openPage();
        po.splashClose();
        Assert.assertTrue(po.isCoral(), "Login and/or Join Buttons are not present");
    }

    }


