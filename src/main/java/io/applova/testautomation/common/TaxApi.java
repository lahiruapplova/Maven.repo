package io.applova.testautomation.common;

import io.applova.testautomation.common.httpRequests.HttpRequest;
import io.applova.testautomation.common.tokens.MerchantToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import io.applova.testautomation.common.utils.Property;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static io.applova.testautomation.common.httpRequests.HttpRequest.sendHttpRequest;

public class TaxApi {
    private static final String tbLink = Property.API_BASE_URL;
    private static  String businessId = Property.businessId;
    private static String merchantEmail = Property.merchantEmail;
    private static String merchantPassword = Property.merchantPassword;
    private static String userEmail = Property.userEmail;
    private static String userPassword = Property.userPassword;

    public static void createTax(String taxName, String taxRate, String taxLevel) {
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/taxes";
        String requestBody = "{\"name\":\""+taxName+"\",\"taxLevel\":\""+taxLevel+"\",\"rate\":\""+taxRate+"\",\"isIncludedInPrice\":false}";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            HttpRequest.sendHttpRequest(url, "POST", token, requestBody);
            System.out.println("success: createTax ✅");
        });
    }

    public static String getAllTaxes(){
        AtomicReference<String> taxes = new AtomicReference<>();
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/taxes";
        System.out.println("business ID passing for the tax API-----"+businessId);
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            taxes.set (HttpRequest.sendHttpRequest(url, "GET", token));
            System.out.println("success: getAllTaxes ✅");
        });
        return taxes.get();
    }

    public static String getTaxDetailById(String taxId){
        String taxesDetails = getAllTaxes();
        String taxDetail = null;
        try {
            JSONArray json = new JSONArray(taxesDetails);
            for (Object jsonObject : json) {
                if(Objects.equals(((JSONObject) jsonObject).getString("id"), taxId)){
                    taxDetail = ((JSONObject) jsonObject).toString();
                    break;
                }
                else{
                    taxDetail = null;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taxDetail;
    }

    public static double getTaxRateById(String taxId){
        String taxDetail = getTaxDetailById(taxId);
        double taxRate = 0.0;
        try {
            JSONObject json = new JSONObject(taxDetail);
            taxRate = json.getDouble("rate");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taxRate;
    }

    public static String getFirstTaxIdFromTaxesList(){
        JSONArray taxDetails = new JSONArray(getAllTaxes());
        String taxId = null;
        try {
            taxId = taxDetails.getJSONObject(0).getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taxId;
    }
}
