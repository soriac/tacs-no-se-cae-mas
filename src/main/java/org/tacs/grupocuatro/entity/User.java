package org.tacs.grupocuatro.entity;

import java.util.Date;
import java.util.Set;

public class User {

    private String id;
    private ApplicationRole role;

    private String username;
    private String password;

    private Date lastLogin;
    private Set<Repository> favoriteRepos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<Repository> getFavRepos() {
        return favoriteRepos;
    }

    public void setFavRepos(Set<Repository> favRepos) {
        this.favoriteRepos = favRepos;
    }

    public ApplicationRole getRole() {
        return role;
    }

    public void setRole(ApplicationRole role) {
        this.role = role;
    }

    public void addFavoriteRepo(Repository repository) {
        favoriteRepos.add(repository);
    }
}
