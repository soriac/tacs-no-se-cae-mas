package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.DAO.RepositoryDAO;
import org.tacs.grupocuatro.DAO.UserDAO;
import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.entity.UserCompareListData;
import org.tacs.grupocuatro.entity.Repository;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;

import java.util.*;
import java.util.stream.Collectors;

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
            ctx.status(200).json(new JsonResponse("").with(user.get()));
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

    public static void compareList(Context ctx) {
        var list1 = ctx.pathParam("list1").trim().split(",");
        var list2 = ctx.pathParam("list2").trim().split(",");

        var repoSet = new HashSet<Repository>();
        var languageSet = new HashSet<String>();

        var sharedRepos = new HashSet<Repository>();
        var sharedLanguages = new HashSet<String>();

        for (var userId : list1) {
            var user = dao.get(userId);

            if (user.isEmpty()){
                continue;
            }

            repoSet.addAll(
                user.get().getFavRepos()
            );

            languageSet.addAll(
                user.get().getFavRepos().stream()
                .map(Repository::getLanguage)
                .collect(Collectors.toList())
            );
        }

        for (var userId : list2) {
            var user = dao.get(userId);

            if (user.isEmpty()){
                continue;
            }

            sharedRepos.addAll(
                user.get().getFavRepos().stream()
                .filter(repoSet::contains)
                .collect(Collectors.toList())
            );

            sharedLanguages.addAll(
                user.get().getFavRepos().stream()
                .map(Repository::getLanguage)
                .filter(languageSet::contains)
                .collect(Collectors.toList())
            );

        }

        ctx.res.setStatus(200);
        if(sharedLanguages.isEmpty() && sharedRepos.isEmpty()){
            ctx.json(new JsonResponse("No hay repositorios ni lenguages en común."));
        } else {
            var data = new UserCompareListData(sharedRepos, sharedLanguages);
            ctx.json(new JsonResponse("Repositorios y lenguajes en común:").with(data));
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

        try {
            var user = dao.get(id.toString());
            var repo = RepositoryDAO.getInstance().getOrAdd(repoId);

            if (user.isEmpty()) {
                ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
            } else {
                user.get().getFavRepos().add(repo);
                ctx.status(200).json(new JsonResponse("Repository added to favorites").with(user.get().getFavRepos()));
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
        var id = ctx.attribute("id");
        var repoId = ctx.pathParam("repo");

        try {
            var user = dao.get(id.toString());
            var repo = RepositoryDAO.getInstance().getOrAdd(repoId);

            if (user.isEmpty()) {
                ctx.status(404).json(new JsonResponse("Invalid Request", "User not found"));
            } else {
                user.get().getFavRepos().remove(repo);
                ctx.status(200).json(new JsonResponse("Repository removed from favorites").with(user.get().getFavRepos()));
            }
        } catch (GitHubRepositoryNotFoundException e) {
            ctx.status(404).json(new JsonResponse("Invalid Request", "Repository not found"));
        } catch (GitHubRequestLimitExceededException e) {
            ctx.status(500).json(new JsonResponse("Invalid Request", "Github Request Limit Exceeded"));
            e.printStackTrace();
        }
    }
}

