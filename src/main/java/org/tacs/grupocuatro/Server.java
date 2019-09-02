package org.tacs.grupocuatro;

import io.javalin.Javalin;
import org.tacs.grupocuatro.controller.AuthenticationController;
import org.tacs.grupocuatro.controller.GitHubController;
import org.tacs.grupocuatro.controller.RepositoryController;
import org.tacs.grupocuatro.controller.UserController;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;
import static org.tacs.grupocuatro.entity.ApplicationRoles.*;

public class Server {
    // poner en otro lado?
    public static boolean DEBUG = true;
    public static int OURPORT  = 8080;
    public static String TEST_STRING =  "Hello, Javelin.";
    
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(OURPORT);
        app.config.accessManager(AuthenticationController::handleAuth);

        app.exception(GitHubRequestLimitExceededException.class, GitHubController::handleRequestLimitExceeded);

        app.get("/", ctx -> ctx.json(new JsonResponse(TEST_STRING)));
        app.routes(() -> {
            before(GitHubController::authenticate);
            before(GitHubController::checkRemainingRequests);

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
                delete("/favorites", UserController::removeFavoriteRepo, roles(USER));
            });
        });
    }

}
