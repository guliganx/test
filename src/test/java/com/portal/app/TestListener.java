package com.portal.app;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;

public class TestListener implements org.testng.ITestListener {
    private static Logger log = LogManager.getLogger(TestListener.class.getName());

    public void onTestSuccess(ITestResult iTestResult) {

    }

    public void onTestFailure(ITestResult iTestResult) {
        String failReason = iTestResult.getThrowable().getMessage();
        ATUReports.add("TEST FAILED", failReason, LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestStart(ITestResult iTestResult) {}
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {}
    public void onStart(ITestContext iTestContext) {}
    public void onFinish(ITestContext iTestContext) {}

}
