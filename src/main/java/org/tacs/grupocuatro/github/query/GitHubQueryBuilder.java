package org.tacs.grupocuatro.github.query;

import org.tacs.grupocuatro.github.enums.*;

public class GitHubQueryBuilder {
	
	// DEFAULT
	private Sort sort = Sort.BEST_MATCH;
	private Order order = Order.DESC;
	private String query = "";
	
	public GitHubQueryBuilder setSort(Sort _sort) {
		this.sort = _sort;
		return this;
	}
	
	public GitHubQueryBuilder setOrder(Order _order) {
		this.order = _order;
		return this;
	}
	
	public GitHubQueryBuilder putDecorator(GitHubQueryDecorator decorator) {
		this.query = query + decorator.build();
		return this;
	}
	
	public GitHubQueryBuilder putKeyword(String keyword) {
		this.query = query + keyword + "+";
		return this;
	}
	
	public String build() {
		
		String str = "?q=";
		str = str + this.query + "&" + sort.getQuery() + "&" + order.getQuery();
		return str;
		
	}
	
}
