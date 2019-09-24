package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.DAO.RepositoryDAO;
import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.JsonResponse;

public class UserController {
    private static UserDAO dao = UserDAO.getInstance();

    public static void all(Context ctx) {
        var users = dao.getAll();
		ctx.status(200).json(new JsonResponse("").with(users));
    }

    public static void one(Context ctx) {
        var id = ctx.pathParam("id");
        var user = dao.get(id);

        if (user.isEmpty()) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else {
            ctx.status(200).json(new JsonResponse("").with(user));
        }
    }

    public static void me(Context ctx) {
    	var id = ctx.attribute("id");
        var user = dao.get(id.toString());

        if (user.isEmpty()) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else {
            ctx.status(200).json(new JsonResponse("").with(user.get()));
        }
    }

    public static void compareFavorites(Context ctx) {
        var id1 = ctx.pathParam("id1");
        var id2 = ctx.pathParam("id2");

        var primero = dao.get(id1);
        var segundo = dao.get(id2);

        if (primero.isEmpty() || segundo.isEmpty()) {
            ctx.res.setStatus(404);
            ctx.json(new JsonResponse("Invalid Request", "One of the users does not exist"));
        } else {
            var reposPrimero = primero.get().getFavRepos();
            var reposSegundo = segundo.get().getFavRepos();

            var reposEnComun = reposPrimero.stream().filter(reposSegundo::contains).toArray();

            ctx.res.setStatus(200);
            ctx.json(new JsonResponse("Repositorios favoritos en común").with(reposEnComun));
        }
    }

    public static void favoriteRepos(Context ctx) {
        var id = ctx.attribute("id");

        var user = dao.get(id.toString());
        if (user.isEmpty()) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else {
            ctx.status(200).json(new JsonResponse("").with(user.get().getFavRepos()));
        }
    }

    public static void addFavoriteRepo(Context ctx) {
        var id = ctx.attribute("id");
        var repoId = ctx.pathParam("repo");

        var user = dao.get(id.toString());
        var repo = RepositoryDAO.getInstance().get(repoId);

        if (user.isEmpty()) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else if (repo.isEmpty()) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "Repository not found"));
        } else {
            user.get().addFavoriteRepo(repo.get());
            ctx.status(200).json(new JsonResponse("Repository added to favorites").with(user.get().getFavRepos()));
        }
    }

    public static void removeFavoriteRepo(Context ctx) {
        var id = ctx.attribute("id");
        var repoId = ctx.pathParam("repo");

        var user = dao.get(id.toString());
        var repo = RepositoryDAO.getInstance().get(repoId);

        if (user.isEmpty()) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else if (repo.isEmpty()) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "Repository not found"));
        } else {
            user.get().getFavRepos().remove(repo.get());
            ctx.status(200).json(new JsonResponse("Repository removed from favorites").with(user.get().getFavRepos()));
        }
    }
}
