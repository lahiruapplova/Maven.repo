package io.applova.testautomation.common;
import io.applova.testautomation.common.tokens.AuthenticationCallback;
import io.applova.testautomation.common.tokens.MerchantToken;
import io.applova.testautomation.common.tokens.UserToken;
import io.applova.testautomation.common.utils.Property;
import io.applova.testautomation.common.utils.PropertyManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class Authentication {

    public static void getMerchantToken(MerchantToken merchantToken, AuthenticationCallback authenticationCallback) {

        System.out.println("start: getMerchantToken ↺");
        PropertyManager prop = new PropertyManager();

        String url = Property.API_BASE_URL +"/merchants/login";
        System.out.println(url);

        String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers("Content-Type", "application/json")
                .body(merchantToken)
                .post(url)
                .then()
                .extract()
                .jsonPath()
                .getString("accessToken");

        System.out.println("success: getMerchantToken ✅");
        authenticationCallback.onComplete(token);
    }

    public static void getUserToken(UserToken userToken, AuthenticationCallback authenticationCallback, String businessId) {
        System.out.println("start: getUerToken ↺");
        String url = Property.API_BASE_URL+"/business/"+businessId+"/user/login";

        String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers("Content-Type", "application/json")
                .body(userToken)
                .post(url)
                .then()
                .extract()
                .jsonPath()
                .getString("token");

        System.out.println("success: getUserToken ✅");
        authenticationCallback.onComplete(token);
    }

    public static String getUserToken(UserToken userToken,String businessId) {
        System.out.println("start: getUerToken ↺");
        String url = Property.API_BASE_URL+"/business/"+businessId+"/user/login";

        String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .headers("Content-Type", "application/json")
                .body(userToken)
                .post(url)
                .then()
                .extract()
                .jsonPath()
                .getString("token");
        return token;
    }

    public static void getUserToken(String userToken, AuthenticationCallback authenticationCallback) {
        authenticationCallback.onComplete(userToken);
    }
}
