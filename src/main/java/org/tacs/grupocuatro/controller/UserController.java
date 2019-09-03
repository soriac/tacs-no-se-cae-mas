package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.JsonResponse;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
    public static final String ID_MOCK = "000";
    public static final String ID_MOCK2 = "000";

    public static void all(Context ctx) {
    			
        var user1 = new HashMap<String, String>();
        user1.put("username", "username_xxx");
        user1.put("favoriteRepoCount", "31");
        user1.put("last_log_in", "20/04/2019");
        user1.put("languages", "java,ruby");
		
        var user2 = new HashMap<String, String>();
        user2.put("username", "username_xxx");
        user2.put("favoriteRepoCount", "12");
        user2.put("last_log_in", "10/10/2001");
        user2.put("languages", "python,html");
        
        var user3 = new HashMap<String, String>();
        user3.put("username", "username_xxx");
        user3.put("favoriteRepoCount", "0");
        user3.put("last_log_in", "11/12/2007");
        user3.put("languages", "javascript,c");
        
        final var users = new ArrayList<HashMap<String, String>>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        
		ctx.res.setStatus(200);
        ctx.json(new JsonResponse("").with(users));
    	
    }

    public static void one(Context context) {
        String id = context.pathParam("id");
        if (!id.equals(ID_MOCK)){
            context.res.setStatus(400);
            context.json(new JsonResponse("The user was not found"));
        }
        else {
            var info = new HashMap<String, String>();
            info.put("username", "username_xxx");
            info.put("favoriteRepoCount", "10");
            info.put("last_log_in", "10/10/2001");
            info.put("languages", "java,ruby");
            context.res.setStatus(200);
            context.json(new JsonResponse("").with(info));
        }
    }

    public static void me(Context ctx) {
    	var userId = ctx.cookieStore("userId");
    	if(userId == null) {
    		ctx.res.setStatus(401);
            ctx.json(new JsonResponse("You must be logged in to access this section."));
    	} else {
    		var info = new HashMap<String, String>();
            info.put("userId", Integer.toString((int) userId));
            ctx.res.setStatus(200);
            ctx.json(new JsonResponse("").with(info));
    	}
    }

    public static void compareFavorites(Context context) {
        String id1 = context.pathParam("id1");
        String id2 = context.pathParam("id2");
        if (!id1.equals(ID_MOCK) || !id2.equals(ID_MOCK2)){
            context.res.setStatus(400);
            context.json(new JsonResponse("The user was not found"));
        }
        var info = new HashMap<String, String>();
        info.put("commonLanguages", "java,ruby");
        info.put("commonRepos", "nameRepo1,nameRepo2");
        context.res.setStatus(200);
        context.json(new JsonResponse("").with(info));
    }

    public static void viewFavoriteRepos(Context ctx) {
        var userId = ctx.attribute("id");

        // obtener repos
        final var mockRepos = new ArrayList<String>();
        mockRepos.add("nodejs/node");
        mockRepos.add("golang/go");
        mockRepos.add("jetbrains/kotlin");


        ctx.json(new JsonResponse("Repos for user ID: " + userId).with(mockRepos));
    }

    public static void addFavoriteRepo(Context ctx) {
        var userId = ctx.attribute("id");
        var payload = ctx.bodyAsClass(repoPayload.class);

        // agregar repo a favoritos
        final var mockRepos = new ArrayList<String>();
        mockRepos.add("nodejs/node");
        mockRepos.add("golang/go");
        mockRepos.add("jetbrains/kotlin");
        mockRepos.add(payload.toString());

        ctx.json(new JsonResponse("Repo added correctly.").with(mockRepos));
    }

    public static void removeFavoriteRepo(Context ctx) {
        var userId = ctx.attribute("id");
        var payload = ctx.bodyAsClass(repoPayload.class);

        // eliminar repo de favoritos
        final var mockRepos = new ArrayList<String>();
        mockRepos.add("nodejs/node");
        mockRepos.add("golang/go");
        mockRepos.add("jetbrains/kotlin");

        var repoToRemove = mockRepos
                .stream()
                .filter(repo -> repo.equals(payload.toString()))
                .findFirst();

        if(repoToRemove.isPresent()) {
            mockRepos.remove(repoToRemove.get());
            ctx.json(new JsonResponse("Repo removed succesfully!").with(mockRepos));
        } else {
            ctx.status(400);
            ctx.json(new JsonResponse("", "Chosen repo is not in user's favorites list!"));
        }
    }
}

class repoPayload {
    private String user;
    private String repo;

    @Override
    public String toString() {
        return user + "/" + repo;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }
}

