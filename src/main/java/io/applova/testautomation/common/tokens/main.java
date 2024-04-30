package io.applova.testautomation.common.tokens;

import io.applova.testautomation.common.CartApi;
import io.applova.testautomation.common.ProductsApi;
import io.applova.testautomation.common.PromoCodeApi;
import io.applova.testautomation.common.TaxApi;
import io.applova.testautomation.common.utils.Property;

public class main {

    public static void main(String[] args) {
        Property property = new Property();
        property.setEnvironment("ENVIRONMENT_TEST_BED");
        property.setApiUrl();
        System.out.println(Property.API_BASE_URL);
        Property.setBusinessId("BIZ_a2yjed7blb3d");
        Property.userEmail = "nuzrahn@hsenidmobile.com";
        Property.userPassword = "applova1234";
        Property.merchantEmail = "applova.qa.automation@gmail.com";
        Property.merchantPassword = "Applova@2021";
        System.out.println(TaxApi.getFirstTaxIdFromTaxesList());
        ProductsApi.assignTaxToProduct("PRD_aahmef3fob3d",TaxApi.getFirstTaxIdFromTaxesList());
    }
}
