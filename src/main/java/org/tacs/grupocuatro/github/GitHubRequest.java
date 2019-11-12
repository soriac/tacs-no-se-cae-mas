package org.tacs.grupocuatro.github;

import org.json.JSONArray;
import org.json.JSONException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.tacs.grupocuatro.github.entity.ContributorsGitHub;
import org.tacs.grupocuatro.github.entity.RepositoriesGitHub;
import org.tacs.grupocuatro.github.entity.RepositoryCommit;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.*;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GitHubRequest {
	
	private static String GITHUB_API = "https://api.github.com/";

	public enum Type{CORE,SEARCH;}
	public String token;

	public GitHubRequest(String _token) {
		this.token = _token;
	}

	public int doTest() {

		HttpClient client = HttpClient.newHttpClient();

		try {

			HttpRequest request = this.httpRequestBuilder()
					.uri(URI.create(GITHUB_API))
					.GET()
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;

	}

	public RepositoriesGitHub doSearchRepository(String query) throws GitHubRequestLimitExceededException {

		HttpClient client = HttpClient.newHttpClient();

		try {

			HttpRequest request = this.httpRequestBuilder()
						.uri(new URI(GITHUB_API + "search/repositories" + query))
						.GET()
						.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if(response.statusCode() == 403) {
				throw new GitHubRequestLimitExceededException();
			} else {

				RepositoriesGitHub repositories = Parser.parseRepositories(response);
				return repositories;

			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public RepositoryGitHub doRepositoryById(long id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
		HttpClient client = HttpClient.newHttpClient();

		try {
			HttpRequest request =  this.httpRequestBuilder()
					.uri(new URI(GITHUB_API + "repositories" + "/" + id))
					.GET()

					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if(response.statusCode() == 403) {
				throw new GitHubRequestLimitExceededException();
			} else if (response.statusCode() == 404) {
				throw new GitHubRepositoryNotFoundException();
		 	} else {

				String resp = response.body();
				JSONObject respJson = new JSONObject(resp);

		 		RepositoryGitHub repo = Parser.parseRepository(respJson);
				return repo;

			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;


	}


	public List<RepositoryCommit> getRepositoryWithCommits(String author, String name) {
		var client = HttpClient.newHttpClient();

		try {
			var path = String.format("repos/%s/%s/commits", author, name);
			var request = this.httpRequestBuilder()
					.uri(new URI(GITHUB_API + path))
					.GET()
					.build();

			var response = client.send(request, HttpResponse.BodyHandlers.ofString());

			JSONArray rawCommits = new JSONArray(response.body());
			List<RepositoryCommit> commits = new ArrayList<>();

			var limite = Math.min(10, rawCommits.length());

			// JSONArray no tiene stream????
			for (int i = 0; i < limite; i++) {
				var info = rawCommits.getJSONObject(i);
				var commit = info.getJSONObject("commit");
				var authorInfo = commit.getJSONObject("author");

				var sha = info.getString("sha");
				var message = commit.getString("message");
				var url = info.getString("html_url");

				var date = authorInfo.getString("date");

				var authorName = authorInfo.getString("name");
				var authorEmail = authorInfo.getString("email");

				commits.add(new RepositoryCommit(sha, message, url, date, authorName, authorEmail));
			}

			return commits;
		} catch (URISyntaxException | JSONException e) {
			// si tira jsonexception es porque me dio un objeto en vez de un array
			// esto pasa porque no existe el repositorio, entonces puedo tirar este error
			throw new RuntimeException("Invalid user or repo name.");
		} catch (InterruptedException | IOException e) {
			throw new RuntimeException("Connection error.");
		}
	}
	
	public ContributorsGitHub doRepositoryContributorsById(long id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
		HttpClient client = HttpClient.newHttpClient();

		try {
			HttpRequest request =  this.httpRequestBuilder()
					.uri(new URI(GITHUB_API + "repositories" + "/" + id + "/contributors"))
					.GET()

					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if(response.statusCode() == 403) {
				throw new GitHubRequestLimitExceededException();
			} else if (response.statusCode() == 404) {
				throw new GitHubRepositoryNotFoundException();
		 	} else {
		 		ContributorsGitHub contributors = Parser.parseContributors(response);
				return contributors;
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Map<String, Integer> getLimits(Type aType) {
		
		HttpClient client = HttpClient.newHttpClient();
		
		try {
			
			HttpRequest request =  this.httpRequestBuilder()
					.uri(new URI(GITHUB_API + "rate_limit"))
					.GET()
					.build();
	
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			String resp = response.body();
			JSONObject respJson = new JSONObject(resp);
			
			Map<String, Integer> ret = new HashMap<String, Integer>();
			
			JSONObject obj = null;
			
			switch(aType) {
				
				case CORE:
					
					obj = respJson
						.getJSONObject("resources")
						.getJSONObject("core");
					
				case SEARCH:
					
					obj = respJson
						.getJSONObject("resources")
						.getJSONObject("search");
					
					break;
					
			}
			
			ret.put("limit", obj.getInt("limit"));
			ret.put("remaining", obj.getInt("remaining"));
			ret.put("reset", obj.getInt("reset"));
			
			return ret;
		
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public List<String> getTags(String urlTags) {
		
		HttpClient client = HttpClient.newHttpClient();
		
		try {
			
			HttpRequest request =  this.httpRequestBuilder()
					.header("Accept", "application/vnd.github.mercy-preview+json")
					.uri(new URI(urlTags))
					.GET()
					.build();
	
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			String resp = response.body();
			JSONObject respJson = new JSONObject(resp);
			
			return Parser.parseTags(respJson);
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private Builder httpRequestBuilder(){
		
		Builder builder = HttpRequest.newBuilder();
		
		if(token != null) {
			builder.header("Authorization", "token " + token);
		}
		
		return builder;
		
	}

	public int createRepo(String name) {
		HttpClient client = HttpClient.newHttpClient();
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		try {
			String requestBody = objectMapper
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(map);
			HttpRequest request = this.httpRequestBuilder()
					.uri(new URI(GITHUB_API + "user/repos"))
					.POST(BodyPublishers.ofString(requestBody))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.statusCode();
		}
		catch (Exception e) {
			return 400;
		}
	}
	
}
