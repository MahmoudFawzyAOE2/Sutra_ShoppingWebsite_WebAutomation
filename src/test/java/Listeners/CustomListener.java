package Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListener implements ITestListener {

    public static boolean testFailed = false;

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Starting test execution: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Finished test execution: " + context.getName());
        if (context.getFailedTests().size() > 0) {
            System.out.println("Some tests failed. Skipping further execution.");
            // You can add logic here to stop further execution if needed
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());
        testFailed = true;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }

    // Implement other methods as needed
}