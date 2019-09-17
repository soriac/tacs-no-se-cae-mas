package org.tacs.grupocuatro.github.enums;

public enum Sort {
	
	BEST_MATCH("sort=best"),
	STARS("sort=stars"),
	FORKS("sort=forks"),
	ISSUES("sort=help-wanted-issues"),
	UPDATED("sort=updated");

	private String query;
	
	Sort(String _query){
		this.query = _query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
}
