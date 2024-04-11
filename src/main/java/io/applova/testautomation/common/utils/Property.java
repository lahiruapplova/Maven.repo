package io.applova.testautomation.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {

    public static String API_BASE_URL;
    private String environment;
    public static String businessId;
    public static String userEmail;
    public static String userPassword;
    public static String merchantEmail;
    public static String merchantPassword;

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public static void setMerchantDetails(String merchantEmail, String merchantPassword){
        Property.merchantEmail = merchantEmail;
        Property.merchantPassword = merchantPassword;
    }

    public static void setUserDetails(String userEmail, String userPassword){
        Property.userEmail = userEmail;
        Property.userPassword = userPassword;
    }

    public static void setBusinessId(String businessId){
        Property.businessId = businessId;
    }

    public static void setBusinessId(String businessId,String scenario){
        Property.businessId = businessId;

    }

//    static {
    public void setApiUrl() {


        Properties properties = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/property.xml");
            properties.loadFromXML(fileInputStream);
            fileInputStream.close();
            // Get the value associated with the specified key
            String apiUrl = properties.getProperty("api_base_url_test_bed");

            // Output the value
            System.out.println("API Base URL: " + apiUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Environment URL setup based on build time ENVIRONMENT
        switch (this.environment) {
            case ("ENVIRONMENT_PRODUCTION"):
               Property.API_BASE_URL = properties.getProperty("api_base_url_production");
                break;
            case ("ENVIRONMENT_PRODUCTION_APPIGO"):
                Property.API_BASE_URL = properties.getProperty("api_base_url_production_appigo");
                break;
            case ("ENVIRONMENT_TEST_BED"):
                Property.API_BASE_URL = properties.getProperty("api_base_url_test_bed");
                break;
            case ("ENVIRONMENT_LOCAL_DEV"):
                Property.API_BASE_URL = properties.getProperty("api_base_url_local_dev");
                break;
            default:
                Property.API_BASE_URL = properties.getProperty("api_base_url_test_bed");
        }
    }

}
