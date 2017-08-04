package com.portal.app;

/**
 * Created by daniel.pino on 08/08/2016.
 */

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzerImpl implements IRetryAnalyzer {
    private static Logger log = LogManager.getLogger(RetryAnalyzerImpl.class.getName());

    private int retryCount = 0;
    private int maxRetryCount = 1;

    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        } else {
            return false;
        }
    }
}