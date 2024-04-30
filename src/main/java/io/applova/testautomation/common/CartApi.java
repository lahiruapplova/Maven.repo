package io.applova.testautomation.common;

import io.applova.testautomation.common.httpRequests.HttpRequest;
import io.applova.testautomation.common.tokens.UserToken;
import io.applova.testautomation.common.utils.CartItem;
import io.applova.testautomation.common.utils.CartPurchase;
import io.applova.testautomation.common.utils.Product;
import io.applova.testautomation.common.utils.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public static void addProductToCart(String productId,String variantId, String userToken) {
        String template = "{\"purchases\":[{\"productId\":\"%s\",\"variantId\":\"%s\",\"cartItemId\":null,\"lineItemId\":\"\",\"quantity\":1,\"addOnSubTypeIds\":[],\"note\":null,\"servingBatchNumber\":1,\"cartSource\":null}],\"reservationId\":null,\"rewardName\":null,\"rewardsId\":0}";
        String requestBody = String.format(template, productId, variantId);
        String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/cart";
        HttpRequest.sendHttpRequest(apiUrl,"PUT" , userToken, requestBody);
        System.out.println("success: addProductToCart ✅");
    }

    public static void addMultipleProductsToCart(List<String> productsId, List<String> variantId) {
        List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < productsId.size(); i++) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productsId.get(i));
            cartItem.setVariantId(variantId.get(i));
            cartItems.add(cartItem);
        }
        CartPurchase cartPurchase = new CartPurchase();
        cartPurchase.setPurchases(cartItems);
        cartPurchase.setReservationId(null);
        cartPurchase.setRewardName(null);
        cartPurchase.setRewardsId(0);
        String requestBody = cartPurchase.toString();
        System.out.println(requestBody);
        Authentication.getUserToken(new UserToken(userEmail, userPassword), (token) -> {
            String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/cart";
            HttpRequest.sendHttpRequest(apiUrl,"PUT" , token, requestBody);
            System.out.println("success: addProductToCart ✅");
        },businessId);
    }

    public static void addProductToCartAsObject(Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(product.getProductId());
        cartItem.setVariantId(product.getVariants().getTypes().get(0).getSku());
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        CartPurchase cartPurchase = new CartPurchase();
        cartPurchase.setPurchases(cartItems);
        cartPurchase.setReservationId(null);
        cartPurchase.setRewardName(null);
        cartPurchase.setRewardsId(0);
        String requestBody = cartPurchase.toString();
        System.out.println(requestBody);
        Authentication.getUserToken(new UserToken(userEmail, userPassword), (token) -> {
            String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/cart";
            HttpRequest.sendHttpRequest(apiUrl,"PUT" , token, requestBody);
            System.out.println("success: addProductToCart ✅");
        },businessId);
    }

    public static void addMultipleProductsToCartAsObject(List<Product> products) {
        List<CartItem> cartItems = new ArrayList<>();
        for (Product product : products) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getProductId());
            cartItem.setVariantId(product.getVariants().getTypes().get(0).getSku());
            cartItem.setAddOnSubTypeIds(product.getAddOns());
            cartItems.add(cartItem);
        }
        CartPurchase cartPurchase = new CartPurchase();
        cartPurchase.setPurchases(cartItems);
        cartPurchase.setReservationId(null);
        cartPurchase.setRewardName(null);
        cartPurchase.setRewardsId(0);
        String requestBody = cartPurchase.toString();
        System.out.println(requestBody);
        Authentication.getUserToken(new UserToken(userEmail, userPassword), (token) -> {
            String apiUrl = Property.API_BASE_URL + "/business/web/" + businessId + "/cart";
            HttpRequest.sendHttpRequest(apiUrl, "PUT", token, requestBody);
            System.out.println("success: addProductToCart ✅");
        }, businessId);
    }
}
