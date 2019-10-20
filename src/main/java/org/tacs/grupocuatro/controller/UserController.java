package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.DAO.RepositoryDAO;
import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;

public class UserController {
    private static UserDAO dao = UserDAO.getInstance();

    public static void all(Context ctx) {
        var users = dao.getAll();
		ctx.status(200).json(new JsonResponse("").with(users));
    }

    public static void one(Context ctx) {
        var id = ctx.pathParam("id");
        var user = dao.get(Long.parseLong(id));

        if (user == null) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else {
            ctx.status(200).json(new JsonResponse("").with(user));
        }
    }

    public static void me(Context ctx) {
    	long id = Long.parseLong(ctx.attribute("id"));
        var user = dao.get(id);

        if (user == null) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else {
            ctx.status(200).json(new JsonResponse("").with(user));
        }
    }

    public static void compareFavorites(Context ctx) {
        var id1 = ctx.pathParam("id1");
        var id2 = ctx.pathParam("id2");

        var primero = dao.get(Long.parseLong(id1));
        var segundo = dao.get(Long.parseLong(id2));

        if (primero == null || segundo == null) {
            ctx.res.setStatus(404);
            ctx.json(new JsonResponse("Invalid Request", "One of the users does not exist"));
        } else {
            var commonRepos = UserDAO.getInstance().getCommonRepos(primero, segundo);
            ctx.res.setStatus(200);
            ctx.json(new JsonResponse("Repositorios favoritos en común").with(commonRepos));
        }
    }

    public static void favoriteRepos(Context ctx) {
        var id = Long.parseLong(ctx.attribute("id"));

        var user = dao.get(id);
        if (user == null) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
        } else {
            ctx.status(200).json(new JsonResponse("").with(user.getFavRepos()));
        }
    }

    public static void addFavoriteRepo(Context ctx) {
        long id = Long.parseLong(ctx.attribute("id"));
        var repoId = ctx.pathParam("repo");

        try {
            var user = dao.get(id);
            var repo = RepositoryDAO.getInstance().getOrAdd(Long.parseLong(repoId));

            if (user == null) {
                ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
            } else {
                user.addFavoriteRepo(repo);
                ctx.status(200).json(new JsonResponse("Repository added to favorites").with(user.getFavRepos()));
            }
        } catch (GitHubRepositoryNotFoundException e) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "Repository not found"));
        } catch (GitHubRequestLimitExceededException e) {
            ctx.status(500).json(new JsonResponse("Invalid Request", "Github Request Limit Exceeded"));
            e.printStackTrace();
        }
    }

    // es exactamente igual a add, pero llama a remove... quizas haya una abstracción acá
    // pasa que molestan mucho los optional y las excpeciones
    public static void removeFavoriteRepo(Context ctx) {
        long id = Long.parseLong(ctx.attribute("id"));
        var repoId = ctx.pathParam("repo");

        try {
            var user = dao.get(id);
            var repo = RepositoryDAO.getInstance().getOrAdd(Long.parseLong(repoId));

            if (user == null) {
                ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
            } else {
                user.removeFavoriteRepo(repo);
                ctx.status(200).json(new JsonResponse("Repository removed from favorites").with(user.getFavRepos()));
            }
        } catch (GitHubRepositoryNotFoundException e) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "Repository not found"));
        } catch (GitHubRequestLimitExceededException e) {
            ctx.status(500).json(new JsonResponse("Invalid Request", "Github Request Limit Exceeded"));
            e.printStackTrace();
        }
    }
}
