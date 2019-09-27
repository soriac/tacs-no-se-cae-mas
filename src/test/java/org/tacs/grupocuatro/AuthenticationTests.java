package org.tacs.grupocuatro;

import io.javalin.plugin.json.JavalinJson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tacs.grupocuatro.controller.AuthenticationPayload;
import org.tacs.grupocuatro.util.DAOManager;
import org.tacs.grupocuatro.util.RequestFactory;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
class AuthenticationTests {

    @BeforeAll
    static void setup() {
        String[] args = {};
        Server.port = 808;
        Server.main(args);

        RequestFactory.setApi("http://localhost:808");
        DAOManager.clearAllDAOs();
    }

    @AfterEach
    void cleanup() {
        RequestFactory.cleanup();
        DAOManager.clearAllDAOs();
    }

    @Test
    void loginAsAdmin() {
        var payload = new AuthenticationPayload();
        payload.setEmail("admin");
        payload.setPassword("1234");

        var response = RequestFactory.post("/login", payload);

        assertNotNull(response);
        assertEquals(response.statusCode(), 201);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNull(body.getError());

        String token = (String) body.getData();
        assertNotNull(token);
    }

    @Test
    void loginFailsOnUnknownUser() {
        var payload = new AuthenticationPayload();
        payload.setEmail("not@auser.com");
        payload.setPassword("1234");

        var response = RequestFactory.post("/login", payload);

        assertNotNull(response);
        assertEquals(response.statusCode(), 404);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNotNull(body.getError());
        assertNull(body.getData());
    }

    @Test
    void loginFailsOnWrongPassword() {
        var payload = new AuthenticationPayload();
        payload.setEmail("admin");
        payload.setPassword("wrong-password");

        var response = RequestFactory.post("/login", payload);

        assertNotNull(response);
        assertEquals(response.statusCode(), 404);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNotNull(body.getError());
        assertNull(body.getData());
    }

    @Test
    void logoutClearsCookies() {
        var response = RequestFactory.post("/logout", null);

        assertNotNull(response);
        assertEquals(response.statusCode(), 200);

        var cookie = response.headers().firstValue("set-cookie");
        assertFalse(cookie.isEmpty());
        assertTrue(cookie.get().contains("Expires"));

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNull(body.getError());
        assertNull(body.getData());
    }

    @Test
    void signupAsUser() {
        var payload = new AuthenticationPayload();
        payload.setEmail("user@email.com");
        payload.setPassword("1234");

        var response = RequestFactory.post("/signup", payload);

        assertNotNull(response);
        assertEquals(response.statusCode(), 201);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);
        var data = (LinkedHashMap<String, String>) body.getData();

        assertNotNull(body.getMessage());
        assertNull(body.getError());
        assertNotNull(data);

        assertEquals(data.get("email"), payload.getEmail());

        // deberia hashear contraseñas
        // eventualmente ni debería mandar password
        assertNotEquals(data.get("password"), payload.getPassword());
    }

    @Test
    void signupFailsOnInvalidEmail() {
        var payload = new AuthenticationPayload();
        payload.setEmail("a");
        payload.setPassword("1234");

        var response = RequestFactory.post("/signup", payload);

        assertNotNull(response);
        assertEquals(response.statusCode(), 400);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNotNull(body.getError());
        assertNull(body.getData());
    }

    @Test
    void signupFailsOnInvalidPassword() {
        var payload = new AuthenticationPayload();
        payload.setEmail("notgoing@towork.com");
        payload.setPassword("a");

        var response = RequestFactory.post("/signup", payload);

        assertNotNull(response);
        assertEquals(response.statusCode(), 400);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNotNull(body.getError());
        assertNull(body.getData());
    }

    @Test
    void getsUserRoleFromCookieWhenAccessingProtectedResources() {
        // first we login
        var payload = new AuthenticationPayload();
        payload.setEmail("admin");
        payload.setPassword("1234");

        var loginResponse = RequestFactory.post("/login", payload);

        assertNotNull(loginResponse);
        assertEquals(loginResponse.statusCode(), 201);

        var loginBody = JavalinJson.fromJson(loginResponse.body(), JsonResponse.class);

        assertNotNull(loginBody.getMessage());
        assertNull(loginBody.getError());

        var rawCookieStore = loginResponse.headers().firstValue("set-cookie");
        assertFalse(rawCookieStore.isEmpty());
        var cookieStore = rawCookieStore.get().substring(rawCookieStore.get().indexOf('=') + 1, rawCookieStore.get().indexOf(';'));
        assertNotNull(cookieStore);

        RequestFactory.setCookieStore(cookieStore);

        // now we can test
        var response = RequestFactory.get("/users/me");

        assertNotNull(response);
        assertEquals(response.statusCode(), 200);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);
        var data = (LinkedHashMap<String, String>) body.getData();

        assertNotNull(body.getMessage());
        assertNull(body.getError());

        assertEquals(data.get("role"), "ADMIN");
    }

    @Test
    void getsUserRoleFromTokenWhenAccessingProtectedResources() {
        // first we login
        var payload = new AuthenticationPayload();
        payload.setEmail("admin");
        payload.setPassword("1234");

        var loginResponse = RequestFactory.post("/login", payload);

        assertNotNull(loginResponse);
        assertEquals(loginResponse.statusCode(), 201);

        var loginBody = JavalinJson.fromJson(loginResponse.body(), JsonResponse.class);

        assertNotNull(loginBody.getMessage());
        assertNull(loginBody.getError());
        var token = (String) loginBody.getData();

        RequestFactory.setToken(token);

        // now we can test
        var response = RequestFactory.get("/users/me");

        assertNotNull(response);
        assertEquals(response.statusCode(), 200);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);
        var data = (LinkedHashMap<String, String>) body.getData();

        assertNotNull(body.getMessage());
        assertNull(body.getError());

        assertEquals(data.get("role"), "ADMIN");
    }

    @Test
    void rejectsUnauthenticatedRequestsOnProtectedResources() {
        var response = RequestFactory.get("/users/me");

        assertNotNull(response);
        assertEquals(response.statusCode(), 401);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNull(body.getData());
    }

    @Test
    void rejectsUnauthorizedRequestsOnProtectedResources() {
        // create a regular user
        var payload = new AuthenticationPayload();
        payload.setEmail("user@email.com");
        payload.setPassword("1234");

        var signupResponse = RequestFactory.post("/signup", payload);

        assertNotNull(signupResponse);
        assertEquals(signupResponse.statusCode(), 201);

        var signupBody = JavalinJson.fromJson(signupResponse.body(), JsonResponse.class);

        assertNotNull(signupBody.getMessage());
        assertNull(signupBody.getError());
        assertNotNull(signupBody.getData());

        // login as the user
        var loginResponse = RequestFactory.post("/login", payload);

        assertNotNull(loginResponse);
        assertEquals(loginResponse.statusCode(), 201);

        var loginBody = JavalinJson.fromJson(loginResponse.body(), JsonResponse.class);

        assertNotNull(loginBody.getMessage());
        assertNull(loginBody.getError());

        var rawCookieStore = loginResponse.headers().firstValue("set-cookie");
        assertFalse(rawCookieStore.isEmpty());
        var cookieStore = rawCookieStore.get().substring(rawCookieStore.get().indexOf('=') + 1, rawCookieStore.get().indexOf(';'));
        assertNotNull(cookieStore);

        RequestFactory.setCookieStore(cookieStore);

        // attempt to reach an admin resource
        var response = RequestFactory.get("/users");

        assertNotNull(response);
        assertEquals(response.statusCode(), 401);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNull(body.getData());
    }

    @Test
    void rejectsInvalidTokensOnProtectedResources() {
        RequestFactory.setToken("not-a-valid-token");

        // attempt to reach an admin resource
        var response = RequestFactory.get("/users");

        assertNotNull(response);
        assertEquals(response.statusCode(), 401);

        var body = JavalinJson.fromJson(response.body(), JsonResponse.class);

        assertNotNull(body.getMessage());
        assertNull(body.getData());
    }

}
