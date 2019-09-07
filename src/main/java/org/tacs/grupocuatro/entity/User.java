package org.tacs.grupocuatro.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class User {
    private int id;

    private String username;

    private Date lastLogin;

    private long password;

    private Set<String> favRepos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }

    public Set<String> getFavRepos() {
        return favRepos;
    }

    public void setFavRepos(Set<String> favRepos) {
        this.favRepos = favRepos;
    }

    public void addFavRepo(String repo){
        try {
            this.favRepos.add(repo);
        }
        catch (Exception e) {
            return;
        }
    }
}
