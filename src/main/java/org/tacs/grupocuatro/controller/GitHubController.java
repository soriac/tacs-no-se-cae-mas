package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;

import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.GitHubConnectionException;

public class GitHubController {
    
	public static void authenticate(Context ctx) throws GitHubConnectionException{	
    	GitHubConnect conn = GitHubConnect.getInstance();
    	conn.tryConnection();
    }

	/*
    public static void checkRemainingRequests(Context ctx) throws GitHubRequestLimitExceededException {
    	
    	GitHubConnect conn = GitHubConnect.getInstance();
    	conn.check();
    	
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
    */
	
    public static void handleConnectionException(GitHubConnectionException t, Context ctx) {
        ctx.status(500);
        ctx.json(new JsonResponse("", "Error ocurred while connecting to GitHub API"));
    }
    
}
