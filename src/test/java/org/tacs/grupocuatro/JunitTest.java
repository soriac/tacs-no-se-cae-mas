package org.tacs.grupocuatro;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tacs.grupocuatro.Server.OURPORT;
import static org.tacs.grupocuatro.Server.TEST_STRING;

class JunitTest {
    @Test
    void runs() {
        assertEquals(1 + 1, 2);
    }

    @Test
    void hello() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:"+OURPORT)
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.11.0")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "0c242ab1-31b1-43fe-a60b-9825b13977ad,cd9261db-403c-4380-8f69-f93d3b5c9b68")
                .addHeader("Host", "localhost:8083")
                .addHeader("accept-encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();


            Response response = client.newCall(request).execute();
            assertEquals(TEST_STRING, response.body().string());

    }

}
