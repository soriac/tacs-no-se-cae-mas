package org.tacs.grupocuatro;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.controller.*;
import org.tacs.grupocuatro.entity.User;
import org.tacs.grupocuatro.github.exceptions.GitHubConnectionException;
import org.tacs.grupocuatro.telegram.TelegramGHBot;
import org.tacs.grupocuatro.telegram.exceptions.TelegramCannotSetWebhookException;
import org.tacs.grupocuatro.telegram.exceptions.TelegramTokenNotFoundException;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;
import static org.tacs.grupocuatro.entity.ApplicationRole.ADMIN;
import static org.tacs.grupocuatro.entity.ApplicationRole.USER;

public class Server {
	
    public static int port = 8080;
    public static String telegram_webhook = System.getenv("GITHUB_TACS_TELEGRAM_URL") + "/bot";

    public static void main(String[] args) throws TelegramTokenNotFoundException, TelegramCannotSetWebhookException{

    	TelegramGHBot bot = TelegramGHBot.getInstance();
		bot.start(telegram_webhook + bot.getToken());

        UserDAO userDao = UserDAO.getInstance();

    	crearAdministrador(userDao);
    	crearUsuario(userDao);

        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins).start(port);

        app.config.accessManager(AuthenticationController::handleAuth);

        app.exception(GitHubConnectionException.class, GitHubController::handleConnectionException);
        app.exception(AuthenticationException.class, (e, ctx) -> ctx.status(401).json(new JsonResponse("Unauthorized.")));

        app.routes(() -> {

            post("/signup", AuthenticationController::signup);
            post("/login", AuthenticationController::login);
            post("/logout", AuthenticationController::logout);

            // Telegram Bot
            post("/bot" + bot.getToken(), TelegramController::update);

            path("/users", () -> {
                path("/me", () -> {
                    get(UserController::me, roles(USER, ADMIN));
                    get("/favorites", UserController::favoriteRepos, roles(USER));
                    post("/favorites/:repo", UserController::addFavoriteRepo, roles(USER));
                    delete("/favorites/:repo", UserController::removeFavoriteRepo, roles(USER));
                });

                get(UserController::all, roles(ADMIN));
                get("/compare/:id1/:id2", UserController::compareFavorites, roles(ADMIN));
                get("/compare-list/:list1/:list2", UserController::compareList, roles(ADMIN));
                get("/:id", UserController::one, roles(ADMIN));
            });

            path("/repos", () -> {
                get(RepositoryController::all, roles(USER, ADMIN));
                get("/count", RepositoryController::count, roles(ADMIN));
                get("/:author/:name/commits", RepositoryController::commits, roles(USER, ADMIN));

                path("/:id", () -> {
                    get(RepositoryController::one, roles(USER, ADMIN));
                    get("/contributors", RepositoryController::contributors, roles(USER, ADMIN));
                });
                post("/create_at_github/:name", RepositoryController::createAtGithub, roles(ADMIN));
            });
        });
    }

    private static void crearAdministrador(UserDAO dao) {
        if (dao.findByUser("admin") != null)
            return;
        var admin = new User();
        admin.setEmail("admin");
        admin.setPassword(BCrypt.withDefaults().hashToString(12, "1234".toCharArray()));
        admin.setRole(ADMIN);
        dao.save(admin);
    }

    private static void crearUsuario(UserDAO dao) {
        if (dao.findByUser("prueba") != null)
            return;
    	var user = new User();
    	user.setEmail("prueba");
    	user.setPassword(BCrypt.withDefaults().hashToString(12, "prueba".toCharArray()));
    	user.setRole(USER);
        dao.save(user);
    }
    
}
