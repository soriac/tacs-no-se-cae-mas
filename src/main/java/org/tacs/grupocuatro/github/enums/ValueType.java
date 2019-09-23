package org.tacs.grupocuatro.github.enums;

public enum ValueType {

	STARS("stars"),
	FORKS("forks"),
	FOLLOWERS("followers"),
	REPO_SIZE("repo_size"),
	TOPICS("topics");

	private String query;
	
	ValueType(String _query){
		this.query = _query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
}
