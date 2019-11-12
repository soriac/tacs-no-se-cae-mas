package org.tacs.grupocuatro.github.entity;

import java.util.List;
import java.util.stream.Collectors;

import org.tacs.grupocuatro.entity.Repository;

public class RepositoryGitHub {

	private long id;
	private String name;
	private int numForks;
	private int numStars;
	private String language;
	private List<String> tags;

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
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public String getTagsAsString() {
		return tags.stream().collect(Collectors.joining(", "));
	}
	
	public RepositoryGitHub(long id, String name, int numForks, int numStars, String language, List<String> tags) {
		this.id = id;
		this.name = name;
		this.numForks = numForks;
		this.numStars = numStars;
		this.language = language;
		this.tags = tags;
	}
	
	public Repository convertToRepository() {
		return new Repository(this.id, this.name, this.language);
	}
	
}
