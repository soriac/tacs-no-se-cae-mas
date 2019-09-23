package org.tacs.grupocuatro.github.query.decorators;

import org.tacs.grupocuatro.github.enums.ValueType;
import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;

public abstract class SearchByNumbers implements GitHubQueryDecorator{

	private ValueType val;
	
	public SearchByNumbers(ValueType val) {
		this.val = val;
	}
	
	@Override
	public String build() {;
		return val.getQuery();
	}
	
}
