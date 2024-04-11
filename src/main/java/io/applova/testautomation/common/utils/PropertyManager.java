package io.applova.testautomation.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class PropertyManager {
    public Properties PropertyReader(String fileName) {
        Properties properties = new Properties();
        String filePath = "configs" + File.separator + fileName+".properties";

        // Get the class loader and use it to load the resource
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Property file '" + filePath + "' not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return properties;
    }

    public String getProperty(String fileName, String key){
        return PropertyReader(fileName).getProperty(key);
    }

    public Properties PropertyReaderNew() {
        Properties properties = new Properties();
        String fileName = System.getProperty("environment")+"-configs";
        String filePath = "configs" + File.separator + fileName+".properties";

        // Get the class loader and use it to load the resource
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Property file '" + filePath + "' not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return properties;
    }


    public String getProp(String key){
        return PropertyReaderNew().getProperty(key);
    }

}
