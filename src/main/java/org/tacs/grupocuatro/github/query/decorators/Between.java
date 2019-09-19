package org.tacs.grupocuatro.github.query.decorators;

import org.tacs.grupocuatro.github.enums.ValueType;

public class Between extends SearchByNumbers{
	
	private int max,min;
	
	public Between(ValueType val,int min, int max) {
		super(val);
		this.max = max;
		this.min = min;
	}

	@Override
	public String build() {
		String str = super.build();
		str = str + "%3A" + String.valueOf(this.min) + ".." + String.valueOf(this.max); 
		return str; 
	}
	
}
