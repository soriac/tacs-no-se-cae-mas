package org.tacs.grupocuatro;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.tacs.grupocuatro.controller.AuthenticationController;
import org.tacs.grupocuatro.controller.RepositoryController;
import org.tacs.grupocuatro.controller.UserController;
import org.tacs.grupocuatro.entity.ApplicationRoles;
import org.tacs.grupocuatro.entity.Repository;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;
import static org.tacs.grupocuatro.entity.ApplicationRoles.*;

public class Server {

    private static ApplicationRoles getUserRole(Context ctx) {
        // debería llamar al userDAO con el id del usuario
        // y devolver el rol de ese usuario
        return ADMIN;
    }

    private static void authenticateGitHub() {
        // hacer alguna magia para autenticarse en github si no estamos autenticados
        // probablemente sea un singleton, ya que si eventualmente tenemos muchas
        // instancias, vamos a tener que autenticar en todas las instancias anyways
    }

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.config.accessManager((handler, ctx, permittedRoles) -> {
            var role = getUserRole(ctx);

            if (permittedRoles.contains(role)) {
                ctx.attribute("role", role);
                handler.handle(ctx);
            } else {
                ctx.status(401).result("Unauthorized.");
            }
        });

        app.get("/", ctx -> ctx.html("Hello, Javelin."));
        app.before(ctx -> authenticateGitHub());

        app.routes(() -> {
            post("/signup", AuthenticationController::signup);
            post("/login", AuthenticationController::login);
            post("/logout", AuthenticationController::logout);

            path("/users", () -> {
                get(UserController::all, roles(ADMIN));
                get("/:id", UserController::one, roles(ADMIN));
                get("/compare", UserController::compareFavorites, roles(ADMIN));
            });

            path("/repos", () -> {
                get(RepositoryController::all, roles(USER, ADMIN));
                get("/:id", RepositoryController::one, roles(USER, ADMIN));
                get("/count", RepositoryController::count, roles(ADMIN));
            });

            path("/me", () -> {
                get(UserController::me, roles(USER));
                post("/favorites", UserController::addFavoriteRepo, roles(USER));
            });
        });
    }

}
