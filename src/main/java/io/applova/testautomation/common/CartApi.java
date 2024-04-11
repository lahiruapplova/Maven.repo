package io.applova.testautomation.common;

import io.applova.testautomation.common.httpRequests.HttpRequest;
import io.applova.testautomation.common.tokens.UserToken;
import io.applova.testautomation.common.utils.Property;

import java.util.List;

import static io.applova.testautomation.common.httpRequests.HttpRequest.sendHttpRequest;

public class CartApi {
    private static  String businessId = Property.businessId;
    private static String userEmail = Property.userEmail;
    private static String userPassword = Property.userPassword;
    public static void  clearCart() {
        System.out.println(businessId);
        String requestBody = "{\"purchases\": [], \"reservationId\": null, \"rewardName\": null, \"rewardsId\": 0}";
        UserToken userToken = new UserToken(userEmail, userPassword);
        System.out.println(userEmail+" "+userPassword);
        Authentication.getUserToken(userToken,(token) -> {

            String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/cart";
            HttpRequest.sendHttpRequest(apiUrl,"PUT" , token, requestBody);
            System.out.println("success: clearCart ✅");
        },businessId);

    }

    public static void addProductToCart(String productId, String variantId) {
        String template = "{\"purchases\":[{\"productId\":\"%s\",\"variantId\":\"%s\",\"cartItemId\":null,\"lineItemId\":\"\",\"quantity\":1,\"addOnSubTypeIds\":[],\"note\":null,\"servingBatchNumber\":1,\"cartSource\":null}],\"reservationId\":null,\"rewardName\":null,\"rewardsId\":0}";
        String requestBody = String.format(template, productId, variantId);
        System.out.println(requestBody);
        Authentication.getUserToken(new UserToken(userEmail, userPassword), (token) -> {
            String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/cart";
            HttpRequest.sendHttpRequest(apiUrl,"PUT" , token, requestBody);
            System.out.println("success: addProductToCart ✅");
        },businessId);
    }

    public static void addMultipleProductsToCart(String businessId,String userEmail,String userPassword,List<String> productsId, List<String> variantId) {
        for (int i = 0; i < productsId.size(); i++) {
            addProductToCart(productsId.get(i), variantId.get(i));
        }
    }
}
