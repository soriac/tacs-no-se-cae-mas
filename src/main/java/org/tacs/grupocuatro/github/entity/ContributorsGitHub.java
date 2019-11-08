package org.tacs.grupocuatro.github.entity;

import java.util.List;

public class ContributorsGitHub {

    private List<ContributorGitHub> contributors;

	private String prevPage;
	private String nextPage;
	private String lastPage;
	private String firstPage;

	public List<ContributorGitHub> getContributors() {
		return contributors;
	}

	public void setContributors(List<ContributorGitHub> contributors) {
		this.contributors = contributors;
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
