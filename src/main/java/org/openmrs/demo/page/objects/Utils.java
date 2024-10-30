package org.openmrs.demo.page.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Utils {

    public static Properties applicationProperties = new Properties();
    public static FileInputStream fis;
    public static FileOutputStream fos;
    public static File propertiesFile;
    public static String filePath = "/src/test/resources/test.properties";

    static {
        try {
            propertiesFile = new File(System.getProperty("user.dir") + filePath);
            fis = new FileInputStream(propertiesFile);

            applicationProperties.load(fis);
        } catch (Exception e) {
            System.out.println("Exception Occurred while reading the properties from properties file: " + filePath);
        }
    }

    public static void readPropertiesFile(String filePath) {
        try {
            propertiesFile = new File(System.getProperty("user.dir") + filePath);
            fis = new FileInputStream(propertiesFile);

            applicationProperties.load(fis);
        } catch (Exception e) {
            System.out.println("Exception Occurred while reading the properties from properties file: " + filePath);
        }
    }

    public static String getTestProperty(String propertyName) {
        return applicationProperties.getProperty(propertyName);
    }

    public static void setTestProperty(String propertyName, String propertyValue) {
        try {
            fos = new FileOutputStream(propertiesFile);
            applicationProperties.setProperty(propertyName, propertyValue);
            applicationProperties.store(fos,"");
        } catch (Exception e) {
            System.out.println("Exception Occurred while set the property to properties file");
        }
    }
}
