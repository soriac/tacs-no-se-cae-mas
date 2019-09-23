package org.tacs.grupocuatro.github.entity;

import java.util.List;

public class RepositoriesGitHub {

	private List<RepositoryGitHub> repos;

	private String prevPage;
	private String nextPage;
	private String lastPage;
	private String firstPage;
	
	public List<RepositoryGitHub> getRepos() {
		return repos;
	}
	
	public RepositoryGitHub getRepositoryByPos(int pos) {
		return repos.get(pos);		
	}
	
	public RepositoryGitHub getRepositoryById(long id) {
		return repos.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
	}
	
	public void setRepos(List<RepositoryGitHub> repos) {
		this.repos = repos;
	}

	public String getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(String prevPage) {
		this.prevPage = prevPage;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

}
