package org.tacs.grupocuatro.controller;

import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.Server;
import org.tacs.grupocuatro.entity.ApplicationRoles;

import java.util.Set;

import static org.tacs.grupocuatro.entity.ApplicationRoles.ADMIN;

public class AuthenticationController {
    public static void signup(Context ctx) {
        // parsamos el cuerpo como una clase, javalin valida
        var user = ctx.bodyAsClass(AuthenticationPayload.class);
        ctx.json(new JsonResponse("Signed up!").with(user));
    }

    public static void login(Context ctx) {
        // cookiestore funciona entre diferentes instancias
        // eventualmente debería ser algun hash o un jwt
        var userId = 1234;
        ctx.cookieStore("userId", userId);
        ctx.json(new JsonResponse("Logged in!").with(userId));
    }

    public static void logout(Context ctx) {
        ctx.clearCookieStore();
        ctx.json(new JsonResponse("Logged out!"));
    }

    private static ApplicationRoles getUserRole(Context ctx) {
        // debería llamar al userDAO con el id del usuario
        // y devolver el rol de ese usuario
        return ADMIN;
    }

    public static void handleAuth(Handler handler, Context ctx, Set<Role> permittedRoles) throws Exception {
        var role = getUserRole(ctx);

        if (permittedRoles.contains(role) || Server.DEBUG && role == ADMIN) {
            ctx.attribute("role", role);
            handler.handle(ctx);
        } else {
            ctx.status(401).result("Unauthorized.");
        }
    }
}

class AuthenticationPayload {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Username: " + username + ", Password: " + password;
    }
}

