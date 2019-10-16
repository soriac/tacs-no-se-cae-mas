package org.tacs.grupocuatro.entity;

import java.util.Date;

public class Repository {

    private String id;
    private String name;
    private Date added;
    private String language;

    public Repository(String id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
