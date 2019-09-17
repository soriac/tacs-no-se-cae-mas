package org.tacs.grupocuatro.github;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


public class GitHubRequest {
	
	private static String GITHUB_API = "https://api.github.com/";
	
	public enum Type{CORE,SEARCH}
	
	public String token;
	
	public GitHubRequest(String _token) {
		this.token = _token;
	}
	
	public int test() {
		
		HttpClient client = HttpClient.newHttpClient();
		
		try {
			
			HttpRequest request =  HttpRequest.newBuilder()
					.uri(new URI(GITHUB_API))
					.GET()
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			return response.statusCode();
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 2;
		}
				
	}
	
	public Map<String, Integer> getLimits(Type aType) throws IOException, InterruptedException, URISyntaxException {
		
		HttpClient client = HttpClient.newHttpClient();
			
		HttpRequest request =  HttpRequest.newBuilder()
				.uri(new URI(GITHUB_API + "rate_limit"))
				.GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		String resp = response.toString();
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
		
		ret.put("remaining", obj.getInt("remaining"));
		ret.put("reset", obj.getInt("reset"));
		
		return ret;
		
	}
	
}
