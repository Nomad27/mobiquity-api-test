import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.reportManagement.OutputUtil;
import utils.reportManagement.extent.ExtentTestManager;


public class BaseTest {

  private static final Logger logger = LogManager.getLogger(BaseTest.class);

  @BeforeSuite(alwaysRun = true)
  public void initializeTestSuite(ITestContext context) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    System.setProperty("current.date", LocalDateTime.now().format(format));
    OutputUtil.createOutputDirectory();

    org.apache.logging.log4j.core.LoggerContext ctx =
        (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
    ctx.reconfigure();

    //logger.info("*** Test Suite " + context.getName() + " started ***");
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult iTestResult) {

    if (iTestResult.getStatus() == ITestResult.SUCCESS) {

    } else if (iTestResult.getStatus() == ITestResult.FAILURE) {
      ExtentTestManager.log(logger, Status.FAIL, iTestResult.getThrowable());
    } else if (iTestResult.getStatus() == ITestResult.SKIP) {
      ExtentTestManager.log(logger, Status.SKIP, iTestResult.getThrowable());
    }

    ExtentTestManager.endTest();
  }

  @AfterSuite(alwaysRun = true)
  public void completeSuite(ITestContext context) {
    logger.info("*** Test Suite " + context.getName() + " ending ***");
  }


}

