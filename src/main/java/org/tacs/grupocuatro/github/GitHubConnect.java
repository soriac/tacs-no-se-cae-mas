package org.tacs.grupocuatro.github;

import org.tacs.grupocuatro.github.query.GitHubQueryBuilder;

public class GitHubConnect {
		
	private static GitHubConnect instance = null;
	private String token = null;
	
	private GitHubConnect() {

		String token = System.getenv("GITHUB_TACS");
		
		if (token == null) {
			System.out.println("WARNING - No esta definida la variable de ambiente GITHUB_TACS. Se procede a usar GitHub sin autorizacion");
		}
		
		this.token = token;
	
	}
	
	public static GitHubConnect getInstance() {
		if(instance == null) {
			instance = new GitHubConnect();
		}
		return instance;
	}
	
	public void tryConnection() throws GitHubConnectionException {
				
		GitHubRequest request = new GitHubRequest(this.token);
		int code = request.test();
		
		if(code != 200) {
			throw new GitHubConnectionException();
		}
		
	}
	
	public void searchRepository() {
		
		//GitHubRequest request = new GitHubRequest(this.token);
		
		
		
	}
	
	
	/*
	public checkLimit() {
		
		GitHubRequest request = new GitHubRequest(this.token);
		int a,b = request.checkLimit();
		
	}
	*/
}
