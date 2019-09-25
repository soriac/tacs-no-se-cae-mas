package org.tacs.grupocuatro;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.javalin.Javalin;
import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.controller.AuthenticationController;
import org.tacs.grupocuatro.controller.GitHubController;
import org.tacs.grupocuatro.controller.RepositoryController;
import org.tacs.grupocuatro.controller.UserController;
import org.tacs.grupocuatro.entity.User;
import org.tacs.grupocuatro.github.exceptions.GitHubConnectionException;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;
import static org.tacs.grupocuatro.entity.ApplicationRole.*;

public class Server {
    // poner en otro lado?
    public static boolean DEBUG = true;
    public static int OURPORT  = 8080;
    public static String TEST_STRING =  "Hello, Javelin.";
    
    public static void main(String[] args){
        crearAdministrador();

    	Javalin app = Javalin.create().start(OURPORT);
        app.config.accessManager(AuthenticationController::handleAuth);

        app.exception(GitHubConnectionException.class, GitHubController::handleConnectionException);
        
        app.get("/", ctx -> ctx.json(new JsonResponse(TEST_STRING)));
        app.routes(() -> {
            before(GitHubController::authenticate);

            post("/signup", AuthenticationController::signup);
            post("/login", AuthenticationController::login);
            post("/logout", AuthenticationController::logout);

            path("/users", () -> {
                path("/me", () -> {
                    get(UserController::me, roles(USER));
                    get("/favorites", UserController::favoriteRepos, roles(USER));
                    post("/favorites/:repo", UserController::addFavoriteRepo, roles(USER));
                    delete("/favorites/:repo", UserController::removeFavoriteRepo, roles(USER));
                });

                get(UserController::all, roles(ADMIN));
                get("/compare/:id1/:id2", UserController::compareFavorites, roles(ADMIN));
                get("/:id", UserController::one, roles(ADMIN));
            });

            path("/repos", () -> {
                get(RepositoryController::all, roles(USER, ADMIN));
                get("/count", RepositoryController::count, roles(ADMIN));
                get("/:id", RepositoryController::one, roles(USER, ADMIN));
            });
        });
    }

    private static void crearAdministrador() {
        var admin = new User();
        admin.setUsername("admin");
        admin.setPassword(BCrypt.withDefaults().hashToString(12, "1234".toCharArray()));
        admin.setRole(ADMIN);
        admin.setId("1");
        UserDAO.getInstance().save(admin);
    }
    
}
