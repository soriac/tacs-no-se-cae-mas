package org.tacs.grupocuatro;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.tacs.grupocuatro.Server.OURPORT;
import static org.tacs.grupocuatro.Server.TEST_STRING;
import static org.tacs.grupocuatro.controller.RepositoryController.*;

class RepoTest {

    @Test
    void getRepoUser() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:"+OURPORT+"/repos/000")
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.11.0")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "e45d7e5f-8a3c-4493-a610-f83b9b5693f9,5eab80f5-f75d-4adb-bc33-9019788a398c")
                .addHeader("Host", "localhost:8083")
                .addHeader("accept-encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();


            Response response = client.newCall(request).execute();
            assertEquals(response.body().string(), REPO_MOCK);


    }



    @Test
    void getRepoAdmin() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:"+OURPORT+"/repos/0000")
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.11.0")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "e45d7e5f-8a3c-4493-a610-f83b9b5693f9,5eab80f5-f75d-4adb-bc33-9019788a398c")
                .addHeader("Host", "localhost:8083")
                .addHeader("accept-encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();


        Response response = client.newCall(request).execute();
        assertEquals(response.body().string(), REPO_MOCK_ADMIN);


    }

    @Test
    void getRepos() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:"+OURPORT+"/repos?language=javascript&forksCount=3&private=true&openIssuesCount=5&stargazersCount=2")
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.11.0")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "326496c8-d5a9-49ae-84e1-178085e2a4ec,78f63a32-48c0-44e5-85e9-353b6bb83baf")
                .addHeader("Host", "localhost:8083")
                .addHeader("accept-encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();
        assertTrue(body.contains(REPO_MOCK_MIN)
        && body.contains(REPO_MOCK_MIN2)
        && body.contains("Query Params recibidos: \n" +
                "language = javascript\n" +
                "forksCount = 3\n" +
                "private = true\n" +
                "openIssuesCount = 5\n" +
                "stargazersCount = 2"));


    }
}
