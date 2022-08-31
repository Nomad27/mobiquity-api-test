package utils.globalConstants;

public class PathConfig {

    private final static String PROPERTIES_TEST = "./src/main/resources/test.properties";
    private final static String PROPERTIES_LOG4J = "./src/main/resources/log4j2.xml";

    private static String OUTPUT_DIR_NAME = "";
    private static String OUTPUT_DIR = "";
    private static String REPORTS_PATH = "";

    public static void setOutputDirName(String outputDirName) {
        OUTPUT_DIR_NAME = outputDirName;
    }

    public static String getOutputDir() {
        return OUTPUT_DIR;
    }

    public static void setOutputDir(String outputDir) {
        OUTPUT_DIR = outputDir;
    }

    public static String getReportsPath() {
        return REPORTS_PATH;
    }

    public static void setReportsPath(String reportsPath) {
        REPORTS_PATH = reportsPath;
    }

    public static String getPropertiesTest() {
        return PROPERTIES_TEST;
    }

}



