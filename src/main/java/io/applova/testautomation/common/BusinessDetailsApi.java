package io.applova.testautomation.common;

import io.applova.testautomation.common.tokens.MerchantToken;
import org.json.JSONObject;
import io.applova.testautomation.common.utils.Property;

import java.util.concurrent.atomic.AtomicReference;

import static io.applova.testautomation.common.httpRequests.HttpRequest.sendHttpRequest;


public class BusinessDetailsApi {
    private static final String tbLink = Property.API_BASE_URL;
    private static  String businessId = Property.businessId;
    private static String merchantEmail = Property.merchantEmail;
    private static String merchantPassword = Property.merchantPassword;

    public static String getBusinessTimeZone() {
        AtomicReference<String> businessDetails = new AtomicReference<>();
        String timezone = null;
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId;
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            businessDetails.set (sendHttpRequest(url, "GET", token));
            System.out.println("success: getBusinessTimeZone ✅");
        });

        String businessInfo = businessDetails.get();
        try {
            JSONObject jsonObject = new JSONObject(businessInfo);
            timezone = jsonObject.getJSONObject("business").getString("timezone");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timezone;
    }

}
