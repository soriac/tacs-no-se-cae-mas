package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.JsonResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
    public static final String ID_MOCK = "000";
    public static final String ID_MOCK2 = "000";

    public static void all(Context ctx) {

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

    public static void me(Context context) {

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

