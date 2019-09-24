package org.tacs.grupocuatro.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.entity.ApplicationRole;
import org.tacs.grupocuatro.entity.User;

import java.util.Date;
import java.util.Set;

import static org.tacs.grupocuatro.entity.ApplicationRole.ANONYMOUS;

public class AuthenticationController {
    private static Algorithm algorithm = Algorithm.HMAC256("el-secreto-para-tacs");
    private static JWTVerifier verifier = JWT.require(algorithm).build();

    public static void signup(Context ctx) {
        var data = ctx.bodyAsClass(AuthenticationPayload.class);
        var user = new User();
        var hashedPassword = BCrypt.withDefaults().hashToString(12, data.getPassword().toCharArray());

        user.setUsername(data.getUsername());
        user.setPassword(hashedPassword);
        user.setRole(ApplicationRole.USER);
        UserDAO.getInstance().save(user);

        // debería omitir el password hasheado
        ctx.status(201).json(new JsonResponse("Signed up!").with(user));
    }

    public static void login(Context ctx) {
        var data = ctx.bodyAsClass(AuthenticationPayload.class);
        var user = UserDAO.getInstance().findByUser(data.getUsername());

        // no existe el usuario
        if (user.isEmpty()) {
            ctx.status(404).json(new JsonResponse("", "User not found, or password is wrong"));
        } else {
            var result = BCrypt.verifyer().verify(data.getPassword().toCharArray(), user.get().getPassword());
            if (!result.verified) {
                // contraseña incorrecta
                ctx.status(404).json(new JsonResponse("", "User not found, or password is wrong"));
            } else {
                user.get().setLastLogin(new Date());
                var token = JWT.create()
                        .withClaim("id", user.get().getId())
                        .withClaim("role", user.get().getRole().toString())
                        .sign(algorithm);

                ctx.cookieStore("token", token);
                ctx.status(201).json(new JsonResponse("Logged in!").with(token));
            }
        }
    }

    public static void logout(Context ctx) {
        ctx.clearCookieStore();
        ctx.json(new JsonResponse("Logged out!"));
    }

    private static ApplicationRole getUserRole(String id) {
        var user = UserDAO.getInstance().get(id);
        if (user.isEmpty()) return ApplicationRole.ANONYMOUS;
        else return user.get().getRole();
    }

    public static void handleAuth(Handler handler, Context ctx, Set<Role> permittedRoles) throws Exception {
        var tokenCookie = ctx.cookieStore("token");
        if (tokenCookie == null) {
            handler.handle(ctx);
            return;
        };


        var token = tokenCookie.toString();

        try {
            var result = verifier.verify(token);
            var id = result.getClaim("id").asString();
            var role = getUserRole(id);

            if (permittedRoles.contains(role) || permittedRoles.isEmpty()) {
                ctx.attribute("id", id);
                ctx.attribute("role", role);
                handler.handle(ctx);
            } else {
                ctx.status(401).result("Unauthorized.");
            }
        } catch (JWTVerificationException ignored) {
            ctx.attribute("id", "-1");
            ctx.attribute("role", ANONYMOUS);
        }
    }
}

class AuthenticationPayload {
    private String username;
    private String password;

    String getUsername() {
        return username;
    }
    String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String toString() {
        return "Username: " + username + ", Password: " + password;
    }
}

