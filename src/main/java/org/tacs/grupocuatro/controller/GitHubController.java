package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.JsonResponse;

import java.util.Random;

public class GitHubController {
    public static void authenticate(Context ctx) {
        // hacer alguna magia para autenticarse en github si no estamos autenticados
        // probablemente sea un singleton, ya que si eventualmente tenemos muchas
        // instancias, vamos a tener que autenticar en todas las instancias anyways
    }

    public static void checkRemainingRequests(Context ctx) throws GitHubRequestLimitExceededException {
        var random = new Random();
        var randomness = random.nextInt(100); // genera un número entre 0 y 99
        System.out.println(randomness);
        // 5% chance de que no queden requests
        if (randomness < 5) {
            throw new GitHubRequestLimitExceededException();
        }
    }

    public static void handleRequestLimitExceeded(GitHubRequestLimitExceededException t, Context ctx) {
        ctx.status(500);
        ctx.json(new JsonResponse("", "GitHub Request Limit Exceeded"));
    }
}
