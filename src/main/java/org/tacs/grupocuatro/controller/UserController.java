package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.JsonResponse;

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

    public static void addFavoriteRepo(Context ctx) {

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
    
    public static void removeFavoriteRepo(Context context) {

    }
}
