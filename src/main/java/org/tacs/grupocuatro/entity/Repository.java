package org.tacs.grupocuatro.entity;

import java.util.Date;

public class Repository {
	
	private String id;
	private String name;
	private Date added;
	
	public Repository(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
}
