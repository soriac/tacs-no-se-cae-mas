package org.tacs.grupocuatro.github.query.decorators;

import org.tacs.grupocuatro.github.enums.Comparator;
import org.tacs.grupocuatro.github.enums.ValueType;

public class Comparison extends SearchByNumbers{

	private Comparator comp;
	private int num;
	
	public Comparison(ValueType val, Comparator comp, int num){
		super(val);
		this.comp = comp;
		this.num = num;
	}
	
	@Override
	public String build() {
		String str = super.build();
		return str + "%3A" + this.comp.getQuery() + String.valueOf(this.num);
	}
	
	
}
