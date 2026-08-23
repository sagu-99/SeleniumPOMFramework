package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        // suite/test start
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getReportInstance().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = buildTestName(result);
        ExtentReportManager.createTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String screenshotPath = ExtentReportManager.captureScreenshot(DriverManager.getDriver(), result.getMethod().getMethodName());
            if (screenshotPath != null) {
                ExtentReportManager.getTest().fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            } else {
                ExtentReportManager.getTest().fail("Test failed - screenshot unavailable");
            }
            ExtentReportManager.getTest().fail(result.getThrowable());
        } catch (Exception e) {
            if (ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().fail("Exception in onTestFailure: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not used
    }

    private String buildTestName(ITestResult result) {
        String method = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params == null || params.length == 0) return method;
        StringBuilder sb = new StringBuilder(method);
        sb.append(" - ");
        Object p0 = params[0];
        if (p0 instanceof java.util.Map) {
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> map = (java.util.Map<String, Object>) p0;
            Object user = map.get("username");
            if (user == null) user = map.get("user");
            Object pass = map.get("password");
            if (user != null) {
                sb.append("user=").append(user);
                if (pass != null) sb.append(", pass=").append(pass);
                return sb.toString();
            }
        }
        for (int i = 0; i < params.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(params[i]);
        }
        return sb.toString();
    }
}