package org.tacs.grupocuatro.github.entity;

import org.tacs.grupocuatro.entity.Repository;

public class RepositoryGitHub {

	private long id;
	private String name;
	private int numForks;
	private int numStars;
	private String language;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumForks() {
		return numForks;
	}
	public void setNumForks(int numForks) {
		this.numForks = numForks;
	}
	public int getNumStars() {
		return numStars;
	}
	public void setNumStars(int numStars) {
		this.numStars = numStars;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public RepositoryGitHub(long id, String name, int numForks, int numStars, String language) {
		this.id = id;
		this.name = name;
		this.numForks = numForks;
		this.numStars = numStars;
		this.language = language;
	}
	
	public Repository convertToRepository() {
		return new Repository(this.id + "", this.name);
	}
	
}
