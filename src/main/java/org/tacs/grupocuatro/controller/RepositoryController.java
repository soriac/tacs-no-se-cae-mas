package org.tacs.grupocuatro.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import io.javalin.http.Context;
import org.tacs.grupocuatro.JsonResponse;

import java.util.Date;
import java.util.HashMap;


public class RepositoryController {
	public static final String ID_MOCK = "000";
	public static final String REPO_MOCK = "{\n" +
			"\t\"id\": \"000\",\n" +
			"\t\"full_name\": \"octocat/Hello-World\",\n" +
			"\t\"url\": \"https://github.com/octocat/Hello-World\",\n" +
			"\t\"description\": \"This your first repo!\",\n" +
			"\t\"language\": \"java\",\n" +
			"\t\"forks_count\": 9,\n" +
			"\t\"created_at\": \"2011-01-26T19:01:12Z\",\n" +
			"\t\"updated_at\": \"2011-01-26T19:14:43Z\",\n" +
			"\t\"private\": false,\n" +
			"\t\"subscribers_count\": 42,\n" +
			"\t\"open_issues_count\": 0,\n" +
			"\t\"stargazers_count\": 80\n" +
			"}";

	public static final String REPO_MOCK_MIN = "{\n" +
			"\t\"id\": \"000\",\n" +
			"\t\"full_name\": \"octocat/Hello-World\",\n" +
			"\t\"url\": \"https://github.com/octocat/Hello-World\",\n" +
			"\t\"open_issues_count\": 0,\n" +
			"\t\"stargazers_count\": 80\n" +
			"\t\"forks_count\": 9,\n" +
			"\t\"language\": \"java\",\n" +
			"\t\"private\": false,\n" +
			"}";

	public static final String REPO_MOCK_MIN2 = "{\n" +
			"\t\"id\": \"0001\",\n" +
			"\t\"full_name\": \"octocat/Hello-World2\",\n" +
			"\t\"url\": \"https://github.com/octocat/Hello-World2\",\n" +
			"\t\"open_issues_count\": 20,\n" +
			"\t\"stargazers_count\": 80,\n" +
			"\t\"forks_count\": 3,\n" +
			"\t\"language\": \"kotlin\",\n" +
			"\t\"private\": false,\n" +
			"}";

	public static final String ID_MOCK_ADMIN = "0000";
	public static final String REPO_MOCK_ADMIN = "{\n" +
			"\t\"id\": \"0000\",\n" +
			"\t\"full_name\": \"octocat/Hello-World\",\n" +
			"\t\"url\": \"https://github.com/octocat/Hello-World\",\n" +
			"\t\"description\": \"This your first repo!\",\n" +
			"\t\"language\": \"java\",\n" +
			"\t\"forks_count\": 9,\n" +
			"\t\"created_at\": \"2011-01-26T19:01:12Z\",\n" +
			"\t\"updated_at\": \"2011-01-26T19:14:43Z\",\n" +
			"\t\"private\": false,\n" +
			"\t\"subscribers_count\": 42,\n" +
			"\t\"open_issues_count\": 0,\n" +
			"\t\"stargazers_count\": 80,\n" +
			"\t\"favorites_count\": 10\n" +
			"}";

	public static void all(Context ctx) {
		ctx.res.setStatus(200);
		String language = ctx.queryParam("language");
		String forksCount = ctx.queryParam("forksCount");
		String isPrivate = ctx.queryParam("private");
		String openIssuesCount = ctx.queryParam("openIssuesCount");
		String stargazersCount = ctx.queryParam("stargazersCount");
		ctx.res.setStatus(200);
		ctx.result("Query Params recibidos: \n" +
						"language = " + language +"\n" +
						"forksCount = " + forksCount + "\n" +
						"private = " + isPrivate+"\n" +
						"openIssuesCount = " + openIssuesCount+"\n" +
						"stargazersCount = " + stargazersCount+"\n" +
						"[\n" +
				REPO_MOCK_MIN + "\n" +
				", " +
				REPO_MOCK_MIN2+"\n" +
				"]"
				);
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

    public static void count(Context ctx) {
		String since = ctx.queryParam("since");
		if (since == null) since = "0";

		int amount = 5000;

		var data = new HashMap<String, String>();
		data.put("amount", "" + amount);
		data.put("from", since);

		var formattedDate = new Date(Long.parseLong(since));

		ctx.res.setStatus(200);
		ctx.json(new JsonResponse("There are " + amount + " repositories available since " + formattedDate.toString() + "."). with(data));
    }
}
