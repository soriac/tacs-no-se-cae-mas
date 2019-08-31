package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;

public class RepositoryController {

	public static final String ID_MOCK = "000";
	public static final String REPO_MOCK = "{\n" +
			"\"\tfull_name\": \"octocat/Hello-World\",\n" +
			"\"\turl\": \"https://github.com/users/octocat\",\n" +
			"\"\tdescription\": \"This your first repo!\",\n" +
			"\"\tlanguage\": null,\n" +
			"\"\tforks_count\": 9,\n" +
			"\"\tcreated_at\": \"2011-01-26T19:01:12Z\",\n" +
			"\"\tupdated_at\": \"2011-01-26T19:14:43Z\",\n" +
			"\"\tprivate\": false,\n" +
			"\"\tsubscribers_count\": 42,\n" +
			"\"\topen_issues_count\": 0,\n" +
			"}";

	public static final String ID_MOCK_ADMIN = "0000";
	public static final String REPO_MOCK_ADMIN = "{\n" +
			"\"\tfull_name\": \"octocat/Hello-World\",\n" +
			"\"\turl\": \"https://github.com/users/octocat\",\n" +
			"\"\tdescription\": \"This your first repo!\",\n" +
			"\"\tlanguage\": null,\n" +
			"\"\tforks_count\": 9,\n" +
			"\"\tcreated_at\": \"2011-01-26T19:01:12Z\",\n" +
			"\"\tupdated_at\": \"2011-01-26T19:14:43Z\",\n" +
			"\"\tprivate\": false,\n" +
			"\"\tsubscribers_count\": 42,\n" +
			"\"\topen_issues_count\": 0,\n" +
			"\"\tstargazers_count\": 80\n" +
			"}";

	public static void all(Context ctx) {
		
    }

    public static void one(Context ctx) {
    	if (ctx.pathParam("id").equals(ID_MOCK)) {
			ctx.res.setStatus(200);
			ctx.result(REPO_MOCK);
		}
		else if (ctx.pathParam("id").equals(ID_MOCK_ADMIN)) {
			ctx.res.setStatus(200);
			ctx.result(REPO_MOCK_ADMIN);
		}
		else {
			ctx.res.setStatus(400);
			ctx.result("Repository with id " + ctx.pathParam("id") + " was not found");
		}
    }

    public static void count(Context context) {

    }
}
