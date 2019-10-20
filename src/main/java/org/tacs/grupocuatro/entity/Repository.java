package org.tacs.grupocuatro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Set;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "repositories")
public class Repository {

	@Id
	private long id;
	private String name;
	private Date added;
	private String language;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "favoriteRepos")
	@JsonBackReference
	private Set<User> users;

	public Repository() {	}

	public Repository(long id, String name, String language) {
		this.id = id;
		this.name = name;
		this.language = language;
	}

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

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
