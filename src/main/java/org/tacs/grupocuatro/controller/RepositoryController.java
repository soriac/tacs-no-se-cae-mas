package org.tacs.grupocuatro.controller;

import io.javalin.http.Context;
import org.tacs.grupocuatro.DAO.RepositoryDAO;
import org.tacs.grupocuatro.JsonResponse;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.enums.*;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;
import org.tacs.grupocuatro.github.query.decorators.Comparison;
import org.tacs.grupocuatro.github.query.decorators.Operation;

import java.util.ArrayList;
import java.util.Date;

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
			var repos = GitHubConnect.getInstance().searchRepository(Order.ASC, Sort.STARS, decorators).getRepos();

			// yo se que el attribute no es null, pero java no es tan inteligente :)
			if ("ADMIN".equals(ctx.attribute("role").toString())) {
				var reposWithFavorites = repos.stream()
						.map(repo ->
								new RepositoryGitHubWithFavCount(
										repo,
										RepositoryDAO.getInstance().favCountForId(repo.getId())
								))
						.toArray();

				ctx.status(200).json(new JsonResponse("").with(reposWithFavorites));
			} else {
				ctx.status(200).json(new JsonResponse("").with(repos));
			}
		} catch (GitHubRequestLimitExceededException ex) {
		    ctx.status(500).json(new JsonResponse("", "Ran out of GitHub requests."));
		}

    }

    public static void one(Context ctx) {
		var id = ctx.pathParam("id");
		try {
			var ghRepo = GitHubConnect.getInstance().findRepositoryById(Long.parseLong(id));
			if ("ADMIN".equals(ctx.attribute("role").toString())) {
				var repoWithFavorites = new RepositoryGitHubWithFavCount(
						ghRepo,
						RepositoryDAO.getInstance().favCountForId(ghRepo.getId())
				);

				ctx.status(200).json(new JsonResponse("").with(repoWithFavorites));
			} else {
				ctx.status(200).json(new JsonResponse("").with(ghRepo));
			}
		} catch (GitHubRepositoryNotFoundException e) {
			ctx.status(404).json(new JsonResponse("Invalid Request", "Repository not found"));
		} catch (GitHubRequestLimitExceededException e) {
			ctx.status(500).json(new JsonResponse("Invalid Request", "Github Request Limit Exceeded"));
			e.printStackTrace();
		}
	}

	public static void count(Context ctx) {
		String since = ctx.queryParam("since");
		if (since == null) since = "0";

		final var startingDate = new Date(Long.parseLong(since));

		var repos = RepositoryDAO.getInstance()
				.getAll().stream()
				.filter(repo -> repo.getAdded().after(startingDate))
				.count();

		ctx.status(200).json(new JsonResponse("").with(repos));

    }

	public static void contributors(Context ctx) {
		var id = ctx.pathParam("id");
		try {
			var contributors = GitHubConnect.getInstance().getRepositoryContributorsById(Long.parseLong(id)).getContributors();
			ctx.status(200).json(new JsonResponse("").with(contributors));
		} catch (GitHubRepositoryNotFoundException e) {
			ctx.status(404).json(new JsonResponse("Invalid Request", "Repository not found"));
		} catch (GitHubRequestLimitExceededException e) {
			ctx.status(500).json(new JsonResponse("Invalid Request", "Github Request Limit Exceeded"));
			e.printStackTrace();
		}
	}

	public static void createAtGithub(Context ctx) {
		String name = ctx.pathParam("name");
		int status = GitHubConnect.getInstance().createRepo(name);
		ctx.status(status).json(new JsonResponse(""));
	}
}

