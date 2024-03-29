package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.github.exceptions.GitHubConnectionException;

public class GitHubController {
    public static void handleConnectionException(GitHubConnectionException t, Context ctx) {
        t.printStackTrace();
        ctx.status(500);
        ctx.json(new JsonResponse("", "Error ocurred while connecting to GitHub API"));
    }
}
