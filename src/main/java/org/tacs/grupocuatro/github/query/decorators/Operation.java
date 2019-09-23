package org.tacs.grupocuatro.github.query.decorators;

import org.tacs.grupocuatro.github.enums.Operator;
import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;

public class Operation implements GitHubQueryDecorator {
		
	private Operator op;
	
	public Operation(Operator _op){
		this.op = _op;
	}
	
	@Override
	public String build() {
		return op.getQuery();
	}
	
}
