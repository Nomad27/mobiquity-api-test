package utils.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import utils.globalConstants.PathConfig;

public final class TestProperties {

  private static final Properties properties;

  static {
    properties = new Properties();
    System.out.println("Loading Properties");
    try {
      FileInputStream inputStream = new FileInputStream(PathConfig.getPropertiesTest());
      properties.load(inputStream);
    } catch (FileNotFoundException ex) {

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getProperty(String propertyName) {

    Object value = properties.get(propertyName);
    if (value != null) {
      return properties.get(propertyName).toString();
    } else {
      String errorLog =
          MessageFormat.format("Error occurred while getting {0} Property from TestProperties file. This could be due to no such property available in TestProperties.properties file.", propertyName);
      throw new RuntimeException(errorLog);
    }
  }

}
