package org.tacs.grupocuatro.github.enums;

public enum Operator {
	
	AND("+"),
	OR("&"),
	NOT("NOT");

	private String query;
	
	Operator(String _query){
		this.query = _query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
}
