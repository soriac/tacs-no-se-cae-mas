package org.tacs.grupocuatro.github.enums;

public enum Comparator {

	LESS("%3C"),
	GREATER("%3E"),
	LESS_EQUALS("%3C%3D"),
	GREATER_EQUALS("%3C%3E");

	private String query;
	
	Comparator(String _query){
		this.query = _query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
}
