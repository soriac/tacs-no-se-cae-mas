package org.tacs.grupocuatro.util;

import io.javalin.plugin.json.JavalinJson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class RequestFactory {
    private static String token = "";
    private static String cookieStore = "";
    private static String api = "";
    private static HttpClient client = HttpClient.newHttpClient();

    public static void cleanup() {
        setCookieStore("");
        setToken("");
    }

    public static void setCookieStore(String cookieStore) {
        RequestFactory.cookieStore = cookieStore;
    }

    public static void setToken(String token) {
        RequestFactory.token = token;
    }

    public static void setApi(String api) {
        RequestFactory.api = api;
    }

    public static HttpResponse<String> get(String path) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(new URI(api + path))
                    .header("Authorization", "Bearer " + token)
                    .header("Cookie", "javalin-cookie-store=" + cookieStore)
                    .GET()
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse<String> post(String path, Object data) {
        // es solo para testing, es una forma "prolija" de poder pasar null
        if (data == null) {
            data = new HashMap<>();
        }

        try {
            var request = HttpRequest.newBuilder()
                    .uri(new URI(api + path))
                    .header("Content-Type", "application/json")
                    .header("Cookie", "javalin-cookie-store=" + cookieStore)
                    .header("Authorization", "Bearer " + token)
                    .header("javalin-cookie-store", cookieStore)
                    .POST(BodyPublishers.ofString(JavalinJson.toJson(data)))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
