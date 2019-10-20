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

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "favoriteRepos")
	@JsonBackReference
	private Set<User> users;

	public Repository() {	}
	
	public Repository(long id, String name) {
		this.id = id;
		this.name = name;
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
