package utils.reportManagement.extent;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentTest;

public class ExtentReporter {

    private static Map<Integer, ExtentTest> reporterMap = new HashMap<Integer, ExtentTest>();

    public static ExtentTest get(Integer integer) {
        return reporterMap.get(integer);
    }

    public static void set(Integer integer, ExtentTest test) {
        reporterMap.put(integer, test);
    }

}
