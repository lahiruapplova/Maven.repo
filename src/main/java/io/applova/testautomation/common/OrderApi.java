package io.applova.testautomation.common;

import io.applova.testautomation.common.httpRequests.HttpRequest;
import io.applova.testautomation.common.tokens.MerchantToken;
import io.applova.testautomation.common.tokens.UserToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import io.applova.testautomation.common.utils.Property;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static io.applova.testautomation.common.httpRequests.HttpRequest.sendHttpRequest;

public class OrderApi {
    private static  String businessId = Property.businessId;
    private static String merchantEmail = Property.merchantEmail;
    private static String merchantPassword = Property.merchantPassword;
    private static String userEmail = Property.userEmail;
    private static String userPassword = Property.userPassword;

    public static void updateOrderStatus(String status,String orderId){
        String requestBody = "[{ \"op\": \"replace\", \"path\": \"/purchaseStatus\", \"value\": \"" + status + "\" }]";
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            String apiUrl = Property.API_BASE_URL + "/mgmt/merchants/businesses/" + businessId + "/purchases/" + orderId;
            HttpRequest.sendHttpRequest(apiUrl, "PUT", token, requestBody);
            System.out.println("success: updateOrderStatus ✅");
        });
    }

    public static void deleteOrder(String orderId){
        UserToken userToken = new UserToken(userEmail, userPassword);
        Authentication.getUserToken(userToken,(token) -> {
            String apiUrl = Property.API_BASE_URL + "/business/" + businessId + "/purchases/" + orderId;
            HttpRequest.sendHttpRequest(apiUrl, "DELETE", token);
            System.out.println("success: deleteOrder ✅");
        },businessId);
    }


    public static String getPurchases(){
        AtomicReference<String> purchases = new AtomicReference<>();
        String url = Property.API_BASE_URL+businessId+"/purchases/lookup/start/0/limit/100";
        UserToken userToken = new UserToken(userEmail, userPassword);
        Authentication.getUserToken(userToken,(token) -> {

            purchases.set(HttpRequest.sendHttpRequest(url, "GET", token));
            System.out.println("success: deleteOrder ✅");
        },businessId);
        return purchases.get();
    }

    public static ArrayList<String> getTransActionIds(){
        ArrayList<String> transactionIds = new ArrayList<>();
        String purchases = getPurchases();
        try {
            JSONArray purchasesList = new JSONObject(purchases).getJSONArray("purchases");

            for (int i = 0; i < purchasesList.length(); i++) {
                JSONObject purchase = purchasesList.getJSONObject(i);
                String transactionId = purchase.getString("transactionId");
                transactionIds.add(transactionId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return transactionIds;
    }

    public static void deleteAllOrders(){
        ArrayList<String> transActionIds = new ArrayList<>();
        transActionIds = OrderApi.getTransActionIds();
        for(String id: transActionIds){
            OrderApi.deleteOrder(id);
        }
    }
}
