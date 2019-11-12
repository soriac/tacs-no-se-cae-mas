package org.tacs.grupocuatro.github;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tacs.grupocuatro.github.entity.ContributorGitHub;
import org.tacs.grupocuatro.github.entity.ContributorsGitHub;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.entity.RepositoriesGitHub;

public class Parser {
	
	
	public static RepositoriesGitHub parseRepositories(HttpResponse<String> response) {
		
		String resp = response.body().toString();
		JSONObject respJson = new JSONObject(resp);
		
		JSONArray jsonArray = respJson.getJSONArray("items");
		
		List<RepositoryGitHub> repos = new ArrayList<RepositoryGitHub>();
		RepositoriesGitHub reposGitHub = new RepositoriesGitHub();
		
		for(int n=0; n<jsonArray.length(); n++) {
			
			JSONObject repoJson = jsonArray.getJSONObject(n);
			
			repos.add(Parser.parseRepository(repoJson));
			
		}
		
		reposGitHub.setRepos(repos);
		
		String headerLinks = response.headers().firstValue("Link").orElse(null);
		
		if(headerLinks == null) {
			
			reposGitHub.setFirstPage(null);
			reposGitHub.setLastPage(null);
			reposGitHub.setNextPage(null);
			reposGitHub.setPrevPage(null);

		} else {
			
			List<String> links = Arrays.asList(response.headers().firstValue("Link").orElse("").split(","));
			
			for(String link : links) {
				
				String[] linkRel = link.split(";");
				String url = linkRel[0].replaceAll("<|>| ", "");

				if(linkRel[1].contains("first")){
					reposGitHub.setFirstPage(url);
				} else if(linkRel[1].contains("last")) {
					reposGitHub.setLastPage(url);

				} else if(linkRel[1].contains("next")) {
					reposGitHub.setNextPage(url);

				} else if(linkRel[1].contains("prev")) {
					reposGitHub.setPrevPage(url);
				}
				
			}
			
		}

		return reposGitHub;
		
	}
	
	public static RepositoryGitHub parseRepository(JSONObject json) {
		
		String urlTags = json.optString("url",null);
		List<String> tags = new ArrayList<String>();
		
		if(urlTags != null) {
			tags = GitHubConnect.getInstance().findTags(urlTags + "/topics");
		}
		
		RepositoryGitHub repo = new RepositoryGitHub(
				json.optLong("id", 0),
				json.optString("full_name", null),
				json.optInt("forks_count", 0),
				json.optInt("stargazers_count", 0),
				json.optString("language", null),
				tags);
		return repo;
		
	}
	
	public static List<String> parseTags(JSONObject json){
		return json.getJSONArray("names").toList().stream()
				.map(object -> Objects.toString(object, null))
				.collect(Collectors.toList());
	}

    public static ContributorsGitHub parseContributors(HttpResponse<String> response) {
		String resp = response.body();

		if (resp.equals("")) {
			return new ContributorsGitHub();
		}

		JSONArray jsonArray = new JSONArray(resp);

		List<ContributorGitHub> contributors = new ArrayList<>();
		ContributorsGitHub contributorsGitHub = new ContributorsGitHub();

		for(int n=0; n<jsonArray.length(); n++) {

			JSONObject contributorJson = jsonArray.getJSONObject(n);

			contributors.add(Parser.parseContributor(contributorJson));

		}

		contributorsGitHub.setContributors(contributors);

		String headerLinks = response.headers().firstValue("Link").orElse(null);

		if(headerLinks == null) {

			contributorsGitHub.setFirstPage(null);
			contributorsGitHub.setLastPage(null);
			contributorsGitHub.setNextPage(null);
			contributorsGitHub.setPrevPage(null);

		} else {

			List<String> links = Arrays.asList(response.headers().firstValue("Link").orElse("").split(","));

			for(String link : links) {

				String[] linkRel = link.split(";");
				String url = linkRel[0].replaceAll("<|>| ", "");

				if(linkRel[1].contains("first")){
					contributorsGitHub.setFirstPage(url);
				} else if(linkRel[1].contains("last")) {
					contributorsGitHub.setLastPage(url);

				} else if(linkRel[1].contains("next")) {
					contributorsGitHub.setNextPage(url);

				} else if(linkRel[1].contains("prev")) {
					contributorsGitHub.setPrevPage(url);
				}

			}

		}

		return contributorsGitHub;
    }

	private static ContributorGitHub parseContributor(JSONObject json) {
		ContributorGitHub repo = new ContributorGitHub(
				json.optLong("id", 0),
				json.optString("login", null),
				json.optString("avatar_url", null),
				json.optString("html_url", null),
				json.optInt("contributions", 0));

		return repo;
	}

}
