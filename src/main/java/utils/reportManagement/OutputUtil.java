package utils.reportManagement;

import java.io.File;

import utils.globalConstants.PathConfig;

public class OutputUtil {

    public static void createOutputDirectory() {
        String timestamp = System.getProperty("current.date");
        String outputDirName = "Report_" + timestamp;

        PathConfig.setOutputDirName(outputDirName);
        PathConfig.setOutputDir("./test-output/extentreport/" + outputDirName);
        PathConfig.setReportsPath(PathConfig.getOutputDir() + File.separator  + File.separator);

        new File(PathConfig.getOutputDir()).mkdir();
        new File(PathConfig.getReportsPath()).mkdir();
    }
}
