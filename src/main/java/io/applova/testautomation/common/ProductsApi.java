package io.applova.testautomation.common;

import io.applova.testautomation.common.httpRequests.HttpRequest;
import io.applova.testautomation.common.tokens.MerchantToken;
import io.applova.testautomation.common.tokens.UserToken;
import io.applova.testautomation.common.utils.Property;
import org.json.JSONObject;
import java.util.concurrent.atomic.AtomicReference;

import static io.applova.testautomation.common.httpRequests.HttpRequest.sendHttpRequest;

public class ProductsApi {
    private static  String businessId = Property.businessId;
    private static String merchantEmail = Property.merchantEmail;
    private static String merchantPassword = Property.merchantPassword;
    private static String userEmail = Property.userEmail;
    private static String userPassword = Property.userPassword;
    public static void DeleteAllFavoriteProducts(){
        UserToken userToken = new UserToken(userEmail, userPassword);
        Authentication.getUserToken(userToken,(token) -> {
            String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/favourites/products";
            HttpRequest.sendHttpRequest(apiUrl, "DELETE", token);
            System.out.println("success: DeleteAllFavoriteProducts ✅");
        },businessId);
    }

    public static String getProductDetails(String productId){
        UserToken userToken = new UserToken(userEmail, userPassword);
        AtomicReference<String> response = new AtomicReference<>();
        Authentication.getUserToken(userToken, (token -> {
            String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/products/" + productId;
            HttpRequest.sendHttpRequest(apiUrl, "GET", token);
            response.set(HttpRequest.sendHttpRequest(apiUrl, "GET", token));
        }), businessId);
        return response.get();
    }

    public static String getProductPrice(String productId){
        JSONObject productDetails = new JSONObject(getProductDetails(productId));
        double amount = productDetails.getJSONObject("variants")
                .getJSONArray("types")
                .getJSONObject(0)
                .getJSONObject("price")
                .getDouble("amount");
        return String.valueOf(amount);
    }

    public static String getAllProductDetails() {
        AtomicReference<String> productDetails = new AtomicReference();
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        Authentication.getMerchantToken(merchantToken, (token -> {
            String apiUrl = Property.API_BASE_URL + "/mgmt/merchants/businesses/" + businessId + "/products";
            productDetails.set(HttpRequest.sendHttpRequest(apiUrl, "GET", token));
            System.out.println("success: getAllProductDetails ✅");
        }));

        return productDetails.get();
    }

    public static void assignTaxToProduct(String productId,String taxId){
        MerchantToken merchantToken = new MerchantToken(merchantEmail, merchantPassword);
        String template = "{\"removedProductIds\":[],\"newProductIds\":[\"%s\"]}";
        String requestBody = String.format(template, productId);
        System.out.println(requestBody);
        Authentication.getMerchantToken(merchantToken,(token) -> {
            String apiUrl = Property.API_BASE_URL + "/mgmt/merchants/businesses/" + businessId + "/taxes/" + taxId + "/bulk-product-update";
            HttpRequest.sendHttpRequest(apiUrl, "POST", token,requestBody);
            System.out.println("success: assignTaxToProduct ✅");
        });
    }
}
