package org.tacs.grupocuatro.github.enums;

public enum Comparator {

	LESS("<"),
	GREATER(">"),
	LESS_EQUALS("<="),
	GREATER_EQUALS(">=");

	private String query;
	
	Comparator(String _query){
		this.query = _query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
}
