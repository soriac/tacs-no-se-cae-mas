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
    public static String telegram_webhook = "https://53af73a8.ngrok.io" + "/bot";

    public static void main(String[] args) throws TelegramTokenNotFoundException, TelegramCannotSetWebhookException{
    	
    	TelegramGHBot bot = TelegramGHBot.getInstance();
		bot.start(telegram_webhook + bot.getToken());
    	
    	crearAdministrador();
    	crearUsuario();
    	
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
        admin.setEmail("admin");
        admin.setPassword(BCrypt.withDefaults().hashToString(12, "1234".toCharArray()));
        admin.setRole(ADMIN);
        admin.setId("1");
        UserDAO.getInstance().save(admin);
    }
    
    private static void crearUsuario() {
    	var user = new User();
    	user.setEmail("prueba");
    	user.setPassword("prueba");
    	user.setRole(USER);
    	user.setId("2");
        UserDAO.getInstance().save(user);
    }
    
}
