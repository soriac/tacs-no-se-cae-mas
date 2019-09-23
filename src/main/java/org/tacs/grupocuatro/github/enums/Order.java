package org.tacs.grupocuatro.github.enums;

public enum Order {

	DESC("order=desc"),
	ASC("order=asc");

	private String query;
	
	Order(String _query){
		this.query = _query;
	}
	
	public String getQuery() {
		return this.query;
	}
	
}
