package io.applova.testautomation.common.httpRequests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static String sendHttpRequest(String apiUrl, String method, String token) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            if (token != null && !token.isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            int responseCode = connection.getResponseCode();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("Response Code: " + responseCode);
                System.out.println("Response Body: " + response.toString());
                return response.toString();
            } finally {
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendHttpRequest(String apiUrl, String method, String token, String requestBody) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            if (token != null && !token.isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("Response Code: " + responseCode);
                System.out.println("Response Body: " + response.toString());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
