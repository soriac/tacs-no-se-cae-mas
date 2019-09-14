package org.tacs.grupocuatro.entity;

import java.util.Date;
import java.util.Set;

public class User extends Loggable{

    private Date lastLogin;

    private Set<String> favRepos;

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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
