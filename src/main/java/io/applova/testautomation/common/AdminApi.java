package io.applova.testautomation.common;

import io.applova.testautomation.common.utils.Property;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AdminApi {

    private static final String tbLink = Property.API_BASE_URL;
    private static  String businessId = Property.businessId;

    public static void addServiceCharge(String newPercentage){
        String url = tbLink + "/admin/merchants/businesses/" + businessId;
        String requestBody = "[{\"op\":\"replace\",\"path\":\"/serviceChargePercentage\",\"value\":\"" + newPercentage + "\"}]";
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse response = httpClient.execute(httpPatch);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeServiceCharges(){
        String url = tbLink + "/admin/merchants/businesses/" + businessId;
        String requestBody = "[{\"op\":\"remove\",\"path\":\"/serviceChargePercentage\",\"value\":\"null\"}]";
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpResponse response = httpClient.execute(httpPatch);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
