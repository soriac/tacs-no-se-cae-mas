package org.tacs.grupocuatro.github.query.decorators;

import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;

public class SearchByName implements GitHubQueryDecorator{
	
	private String nameRepo;
	
	public SearchByName(String nameRepo){
		this.nameRepo = nameRepo;
	}
	
	@Override
	public String build() {
		String query = "in%3A" + nameRepo.replace(" ", "+"); 
		return query;
	}
	
}
