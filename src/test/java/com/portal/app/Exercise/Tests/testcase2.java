package com.portal.app.Exercise.Tests;

import com.portal.app.AbstractTest;
import com.portal.app.Exercise.Pages.PageObject;
import com.portal.app.Exercise.Pages.betcoral;
import com.portal.app.Exercise.model.Selection;
import com.portal.app.RetryAnalyzerImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;
import spring.User;

public class testcase2 extends AbstractTest {
    private Logger log = LogManager.getLogger(testcase2.class.getName());

    @Autowired
    @Qualifier("testBean")
    private User user;

    //Application Context device=iPhone 6 ; platform=iOS

    @Test(retryAnalyzer=RetryAnalyzerImpl.class)
    public void testCase2() {
        betcoral bc = new betcoral();
        bc.openPage();
        Assert.assertFalse(bc.isUserLoggedInOnMobile(), "The user was already logged in.");
        bc.clickSportFromCarousel();
        Assert.assertEquals(bc.getSelectedTab(), "Matches");
        bc.closeTutorial();
        Selection oddsCardselection = bc.clickFirstAvailableSelection();
        //System.out.println("Selection name = " + selection.getName());
        //System.out.println("Selection price = " + selection.getPrice());
        //open betslip
        // Selection betslipSelection = getFirstSelectionFromBetslip()
        Assert.assertEquals(oddsCardselection.getName(), betslipSelection.getName());



    }


}
