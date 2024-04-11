package io.applova.testautomation.common;

import io.applova.testautomation.common.httpRequests.HttpRequest;
import io.applova.testautomation.common.tokens.MerchantToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import io.applova.testautomation.common.utils.Property;
import io.applova.testautomation.common.utils.PropertyManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

import static io.applova.testautomation.common.httpRequests.HttpRequest.sendHttpRequest;

public class PromoCodeApi {
    private static final String tbLink = Property.API_BASE_URL;
    private static  String businessId = Property.businessId;
    private static String merchantEmail = Property.merchantEmail;
    private static String merchantPassword = Property.merchantPassword;

    private static String getThePromoCodeDetails(){
        AtomicReference<String> promoCodeDetails = new AtomicReference<>();
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/promo/";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            promoCodeDetails.set(HttpRequest.sendHttpRequest(url, "GET", token));
            System.out.println("success: getThePromoCodeDetails ✅");
        });
        return promoCodeDetails.get();
    }

    public static String getPromCode( int index) {
        String details = getThePromoCodeDetails();
        String promoCode = null;
        String promoCodeId = null;
        try {
            JSONObject json = new JSONObject(details);
            JSONArray promoPlans = json.getJSONArray("promoPlans");
            promoCode = promoPlans.getJSONObject(index).getString("promoCode");
            promoCodeId = promoPlans.getJSONObject(index).getString("id");
        } catch (JSONException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        System.out.println(promoCodeId);
        return promoCode;
    }

    public static JSONArray getPromoCodesArray() {
        String details = getThePromoCodeDetails();
        JSONArray promoCodesArray = new JSONArray();
        try {
            JSONObject json = new JSONObject(details);
            promoCodesArray = json.getJSONArray("promoPlans");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return promoCodesArray;
    }

    private static JSONObject getPromoCodeDetailsById(String targetPromoCodeId) {
        String details = getThePromoCodeDetails();
        try {
            JSONObject json = new JSONObject(details);
            JSONArray promoPlans = json.getJSONArray("promoPlans");
            for (int i = 0; i < promoPlans.length(); i++) {
                JSONObject promoCode = promoPlans.getJSONObject(i);
                if (promoCode.has("id") && targetPromoCodeId.equals(promoCode.getString("id"))) {
                    return promoCode;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPromoCodeById(String promoCodeId){
        String details = getThePromoCodeDetails();
        try {
            JSONObject json = new JSONObject(details);
            JSONArray promoPlans = json.getJSONArray("promoPlans");
            for (int i = 0; i < promoPlans.length(); i++) {
                JSONObject promoCode = promoPlans.getJSONObject(i);
                if (promoCode.has("id") && promoCodeId.equals(promoCode.getString("id"))) {
                    return promoCode.getString("promoCode");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject setPromoCodeStartDate(JSONObject jsonObject,int days){
        String newStartingDateStr = generateDates(days);
        String newEndingDateStr = generateDates(days+2);
        System.out.println(newStartingDateStr);
        jsonObject.getJSONObject("promoPeriod")
                .getJSONObject("expiryPromoRule")
                .put("startDate", newStartingDateStr)
                .put("expiryDate", newEndingDateStr);
        return jsonObject;
    }

    private static String generateDates(int plusDays) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime newDate = today.plusDays(plusDays);
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME;
        String newDateString = newDate.format(isoFormatter);
        int indexOfT = newDateString.indexOf('T');
        String datePart = newDateString.substring(0, indexOfT);
        newDateString = datePart + "T16:00-07:00";
        return newDateString;
    }

    public static void changeStartingDateAndEndOfPromoCode(String promoCodeId,int days){
        JSONObject PromoCodejsonObject = getPromoCodeDetailsById(promoCodeId);
        PromoCodejsonObject = setPromoCodeStartDate(PromoCodejsonObject,days);
        String requestBody = PromoCodejsonObject.toString(2);
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/promo/update";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            HttpRequest.sendHttpRequest(url, "POST", token, requestBody);
            System.out.println("success: changeStartingDateOfPromoCode ✅");
        });
    }

    public static void changePromoCodeDateToAValidDate(String promoCodeId){
        JSONObject PromoCodejsonObject = getPromoCodeDetailsById(promoCodeId);
        PromoCodejsonObject = setPromoCodeStartDate(PromoCodejsonObject,-1);
        String requestBody = PromoCodejsonObject.toString(2);
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/promo/update";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            HttpRequest.sendHttpRequest(url, "POST", token, requestBody);
            System.out.println("success: changePromoCodeDateToAValidDate ✅");
        });
    }

    public static void changeMinimumAmountOfPromoCode(String promoCodeId, String productId){
        String productPrice = ProductsApi.getProductPrice(productId);
        double productPriceDouble = Double.parseDouble(productPrice);
        productPriceDouble+=10.0;
        JSONObject jsonObject = getPromoCodeDetailsById(promoCodeId);
        jsonObject.getJSONObject("promoCriteria")
                .put("minimumTotalAmount", productPriceDouble);
        String requestBody = jsonObject.toString(2);
        System.out.println("reuest body for changeMinimumAmountOfPromoCode: "+requestBody);
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/promo/update";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            HttpRequest.sendHttpRequest(url, "POST", token, requestBody);
            System.out.println("success: changeMinimumAmountOfPromoCode ✅");
        });
    }

    public static void setPromoCodeUsageLimit(int usageCount,String promoCodeId){
        JSONObject jsonObject = getPromoCodeDetailsById(promoCodeId);
        if (jsonObject.has("usagePromoRule")) {
            JSONObject usagePromoRule = jsonObject.getJSONObject("usagePromoRule");
            if (usagePromoRule.has("usageCount")) {
                usagePromoRule.put("usageCount", usageCount);
            } else {
                usagePromoRule.put("usageCount", usageCount);
            }
        } else {
            JSONObject usagePromoRule = new JSONObject();
            usagePromoRule.put("usageCount", usageCount);
            jsonObject.put("usagePromoRule", usagePromoRule);
        }
        String requestBody = jsonObject.toString(2);
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/promo/update";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            HttpRequest.sendHttpRequest(url, "POST", token, requestBody);
            System.out.println("success: setPromocodeUsageLimit ✅");
        });
    }

    public static int getPromoCodeUsageLimit(String promoCodeId){
        JSONObject promoCodeDetails = getPromoCodeDetailsById(promoCodeId);
        if (promoCodeDetails.has("usagePromoRule")) {
            JSONObject usagePromoRule = promoCodeDetails.getJSONObject("usagePromoRule");
            if (usagePromoRule.has("usageCount")) {
                return usagePromoRule.getInt("usageCount");
            }
        }
        return 0;
    }

    public static double getPromoCodePercentage(String promoCodeId){
        JSONObject promoCodeDetails = getPromoCodeDetailsById(promoCodeId);
        if (promoCodeDetails.has("promoCriteria")) {
            JSONObject discountPromoRule = promoCodeDetails.getJSONObject("promoCriteria");
            if (discountPromoRule.has("totalAmount")) {
                JSONObject totalAmount = discountPromoRule.getJSONObject("totalAmount");
                return Double.valueOf(totalAmount.get("percentage").toString()) ;
            }
        }
        return 0.0;
    }

    public static void createPromoCode(){
        String requestBody = createPromoCodeRequestBody();
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/promo/create";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            HttpRequest.sendHttpRequest(url, "POST", token, requestBody);
            System.out.println("success: createPromoCode ✅");
        });
    }
    private static String createPromoCodeRequestBody(){
        //  {"promoCode":"Create Promo code","businessId":"BIZ_a2nv8ee3rb81","title":"rerer","description":"create promo","promoPeriod":{"expiryPromoRule":{"startDate":"2024-03-18T14:27+05:30","expiryDate":"2024-03-19T14:27+05:30"}},"usagePromoRule":{"usageCount":""},"promoCriteria":{"minimumTotalAmount":"7","totalAmount":{"percentage":"12.6"}},"maximumPromoAmount":"","timezone":"Asia/Kolkata","displayOnClientApp":false,"status":"ACTIVE"}
        String promoCodeTemplate = "{\"promoCode\":\"%s\",\"businessId\":\"%s\",\"title\":\"%s\",\"description\":\"%s\",\"promoPeriod\":{\"expiryPromoRule\":{\"startDate\"" +
                ":\"%s\",\"expiryDate\":\"%s\"}},\"usagePromoRule\":{\"usageCount\":\"%s\"},\"promoCriteria\":{\"minimumTotalAmount\":" +
                "\"%s\",\"totalAmount\":{\"percentage\":\"%s\"}},\"maximumPromoAmount\":\"%s\",\"timezone\":\"%s\",\"displayOnClientApp\":%s,\"status\":\"%s\"}";
        String promoCode = RandomStringUtils.randomAlphabetic(6);
        String promoCodeJson = String.format(promoCodeTemplate, promoCode, businessId, "Test generated", "create promo", "2024-03-18T14:27+05:30", "2024-03-19T14:27+05:30", "", "7", "12.6", "", BusinessDetailsApi.getBusinessTimeZone(), "false", "ACTIVE");

        return promoCodeJson;

    }

    public static void deletePromoCodeById(String promoCodeId){
        String url = tbLink+"/mgmt/merchants/businesses/"+businessId+"/promo/remove";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        String requestBody = "{\"id\":\""+promoCodeId+"\"}";
        Authentication.getMerchantToken(merchantToken,(token) -> {
            HttpRequest.sendHttpRequest(url, "POST", token,requestBody);
            System.out.println("success: deletePromoCodeById ✅");
        });
    }

    public static void deleteLastPromoCode(){
        JSONArray promoCodeArray = getPromoCodesArray();
        if (!promoCodeArray.isEmpty()) {
            String promoCodeId = promoCodeArray.getJSONObject(promoCodeArray.length()-1).getString("id");
            deletePromoCodeById(promoCodeId);
        }
    }
}
