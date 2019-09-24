package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.DAO.RepositoryDAO;
import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.entity.RepositoriesGitHub;
import org.tacs.grupocuatro.github.enums.*;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.github.query.*;
import org.tacs.grupocuatro.github.query.decorators.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RepositoryController {
    private static GitHubQueryDecorator stringToDecorator(ValueType type, String string) {
    	var separated = string.split(":");
    	var value = Integer.parseInt(separated[1]);
		Comparator comparator;
		switch(separated[0]) {
			case "geq":
			    comparator = Comparator.GREATER_EQUALS;
				break;
			case "leq":
				comparator = Comparator.LESS_EQUALS;
				break;
			case "lt":
				comparator = Comparator.LESS;
				break;
			default:
				comparator = Comparator.GREATER;
				break;
		}

		return new Comparison(type, comparator, value);
	}

	public static void all(Context ctx) {
		// ejemplos de los params:
		// "ge:150"
		// "leq:30"

		String size = ctx.queryParam("size");
		String forks = ctx.queryParam("forks");
		String stars = ctx.queryParam("stars");
		String topics = ctx.queryParam("topics");
		String followers = ctx.queryParam("followers");

		var decorators = new ArrayList<GitHubQueryDecorator>();

		if (size != null) {
		    decorators.add(stringToDecorator(ValueType.REPO_SIZE, size));
		}

		if (forks != null) {
			if (!decorators.isEmpty()) {
				decorators.add(new Operation(Operator.AND));
			}

			decorators.add(stringToDecorator(ValueType.FORKS, forks));
		}

		if (stars != null) {
			if (!decorators.isEmpty()) {
				decorators.add(new Operation(Operator.AND));
			}

			decorators.add(stringToDecorator(ValueType.STARS, stars));
		}

		if (topics != null) {
			if (!decorators.isEmpty()) {
				decorators.add(new Operation(Operator.AND));
			}

			decorators.add(stringToDecorator(ValueType.TOPICS, topics));
		}

		if (followers != null) {
			if (!decorators.isEmpty()) {
				decorators.add(new Operation(Operator.AND));
			}

			decorators.add(stringToDecorator(ValueType.FOLLOWERS, followers));
		}

		try {
			var repos = GitHubConnect.getInstance().searchRepository(Order.ASC, Sort.STARS, decorators);
			ctx.status(200).json(new JsonResponse("").with(repos.getRepos()));
		} catch (GitHubRequestLimitExceededException ex) {
		    ctx.status(500).json(new JsonResponse("", "Ran out of GitHub requests."));
		}

    }

    public static void one(Context ctx) {
    }

    public static void count(Context ctx) {
		String since = ctx.queryParam("since");
		if (since == null) since = "0";

		final var startingDate = new Date(since);

		var repos = RepositoryDAO.getInstance()
				.getAll().stream()
				.filter(repo -> repo.getAdded().after(startingDate));

		ctx.status(200).json(new JsonResponse("").with(repos));

    }
}
