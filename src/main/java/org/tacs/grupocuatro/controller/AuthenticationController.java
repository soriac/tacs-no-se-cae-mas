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

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class AuthenticationController {
    private static Algorithm algorithm = Algorithm.HMAC256("el-secreto-para-tacs");
    private static JWTVerifier verifier = JWT.require(algorithm).build();

    public static void signup(Context ctx) {
        var data = ctx.bodyAsClass(AuthenticationPayload.class);

        if (data.getEmail().length() < 2 | !data.getEmail().contains("@")) {
            ctx.status(400).json(new JsonResponse("Signup failed", "Invalid email"));
            return;
        }

        if (data.getPassword().length() < 2) {
            ctx.status(400).json(new JsonResponse("Signup failed", "Invalid password"));
            return;
        }


        var user = new User();
        var hashedPassword = BCrypt.withDefaults().hashToString(12, data.getPassword().toCharArray());

        user.setEmail(data.getEmail());
        user.setPassword(hashedPassword);
        user.setRole(ApplicationRole.USER);
        UserDAO.getInstance().save(user);

        // TODO: debería omitir el password hasheado
        ctx.status(201).json(new JsonResponse("Signed up!").with(user));
    }

    public static void login(Context ctx) {
        var data = ctx.bodyAsClass(AuthenticationPayload.class);
        var userDao = UserDAO.getInstance();
        User user = userDao.findByUser(data.getEmail());

        // no existe el usuario
            if (user == null) {
            ctx.status(404).json(new JsonResponse("", "User not found, or password is wrong"));
        } else {
            var result = BCrypt.verifyer().verify(data.getPassword().toCharArray(), user.getPassword());
            if (!result.verified) {
                // contraseña incorrecta
                ctx.status(404).json(new JsonResponse("", "User not found, or password is wrong"));
            } else {
                user.setLastLogin(new Date());
                userDao.save(user);
                var token = JWT.create()
                        .withClaim("id", user.getId() + "")
                        .withClaim("role", user.getRole().toString())
                        .sign(algorithm);

                ctx.cookieStore("token", token);
                ctx.status(201).json(new JsonResponse("Logged in!").with(token));
            }
        }
    }

    public static void logout(Context ctx) {
        ctx.clearCookieStore();
        ctx.status(200).json(new JsonResponse("Logged out!"));
    }

    private static ApplicationRole getUserRole(long id) {
        var user = UserDAO.getInstance().get(id);
        if (user == null) return ApplicationRole.ANONYMOUS;
        else return user.getRole();
    }

    public static void handleAuth(Handler handler, Context ctx, Set<Role> permittedRoles) throws Exception {
        var tokenCookie = ctx.cookieStore("token");
        var tokenHeader = ctx.header("Authorization");

        String token;
        if (tokenCookie != null) {
            token = tokenCookie.toString();
        } else if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.replace("Bearer ", "");
        } else if (permittedRoles.isEmpty()) {
            handler.handle(ctx);
            return;
        } else {
            throw new AuthenticationException();
        }

        try {
            var result = verifier.verify(token);
            var id = result.getClaim("id").asString();
            // mejor que lea el role desde el claim del jwt,
            // pasa que hay que hacer una funcion que haga string -> applicationrole
            var role = getUserRole(Long.parseLong(id));

            if (permittedRoles.contains(role)) {
                ctx.attribute("id", id);
                ctx.attribute("role", role);
                handler.handle(ctx);
            } else {
                throw new AuthenticationException();
            }
        } catch (JWTVerificationException ignored) {
            throw new AuthenticationException();
        }
    }
}

