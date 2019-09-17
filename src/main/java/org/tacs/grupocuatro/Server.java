package org.tacs.grupocuatro;

import io.javalin.Javalin;
import org.tacs.grupocuatro.controller.AuthenticationController;
import org.tacs.grupocuatro.controller.GitHubController;
import org.tacs.grupocuatro.controller.RepositoryController;
import org.tacs.grupocuatro.controller.UserController;

import org.tacs.grupocuatro.github.*;
import org.tacs.grupocuatro.github.enums.*;
import org.tacs.grupocuatro.github.query.*;
import org.tacs.grupocuatro.github.query.decorators.*;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;
import static org.tacs.grupocuatro.entity.ApplicationRoles.*;

public class Server {
    // poner en otro lado?
    public static boolean DEBUG = true;
    public static int OURPORT  = 8080;
    public static String TEST_STRING =  "Hello, Javelin.";
    
    public static void main(String[] args) throws GitHubConnectionException {
        
    	/*
    	GitHubConnect conn = GitHubConnect.getInstance();
    	conn.tryConnection();
    	
    	GitHubQueryBuilder gb = new GitHubQueryBuilder()
    			.setSort(Sort.STARS)
    			.setOrder(Order.ASC)
    			.putDecorator(new Comparison(ValueType.STARS, Comparator.GREATER, 5))
    			.putDecorator(new Operation(Operator.AND))
    			.putDecorator(new Between(ValueType.FORKS,10,20));
    	
    	System.out.println(gb.build());
    	*/
    	
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
                get(UserController::all, roles(ADMIN));
                get("/compare", UserController::compareFavorites, roles(ADMIN));
                get("/:id", UserController::one, roles(ADMIN));
            });

            path("/repos", () -> {
                get(RepositoryController::all, roles(USER, ADMIN));
                get("/count", RepositoryController::count, roles(ADMIN));
                get("/:id", RepositoryController::one, roles(USER, ADMIN));
            });

            path("/me", () -> {
                before(ctx -> ctx.attribute("id", ctx.cookieStore("userId")));

                get(UserController::me, roles(USER));
                get("/favorites", UserController::viewFavoriteRepos, roles(USER));
                post("/favorites", UserController::addFavoriteRepo, roles(USER));
                delete("/favorites", UserController::removeFavoriteRepo, roles(USER));
            });
        });
        
    	
    }

}
