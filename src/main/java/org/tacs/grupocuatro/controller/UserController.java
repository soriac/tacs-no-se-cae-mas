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

    public static void compareList(Context ctx) {
        var list1 = Arrays.asList(ctx.pathParam("list1").trim().split(","));
        var list2 = Arrays.asList(ctx.pathParam("list2").trim().split(","));

        List<String> repeated = new LinkedList<String>(list1);
        repeated.retainAll(list2);
        if (repeated.size()>0) {
            ctx.status(400).json(new JsonResponse("Las listas deben ser completamente distintas."));
        } else {
            var repoSet = new HashSet<Repository>();
            var languageSet = new HashSet<String>();

            var sharedRepos = new HashSet<Repository>();
            var sharedLanguages = new HashSet<String>();

            for (var userId : list1) {
                var user = dao.get(Integer.parseInt(userId));

                repoSet.addAll(
                        user.getFavRepos()
                );

                languageSet.addAll(
                        user.getFavRepos().stream()
                                .map(Repository::getLanguage)
                                .collect(Collectors.toList())
                );
            }

            for (var userId : list2) {
                var user = dao.get(Integer.parseInt(userId));

                sharedRepos.addAll(
                        user.getFavRepos().stream()
                                .filter(repo1 -> {
                                    var match = repoSet.stream()
                                                        .filter(repo2 -> repo1.getId() == repo2.getId())
                                                        .collect(Collectors.toList());

                                    return match.size()>0;
                                })
                                .collect(Collectors.toList())
                );

                sharedLanguages.addAll(
                        user.getFavRepos().stream()
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

