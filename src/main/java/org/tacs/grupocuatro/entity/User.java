package org.tacs.grupocuatro.entity;

import java.util.Date;
import java.util.Set;

public class User extends Loggable{

    private Date lastLogin;
    private Set<Repository> favRepos;

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Set<Repository> getFavRepos() {
        return favRepos;
    }

    public void setFavRepos(Set<Repository> favRepos) {
        this.favRepos = favRepos;
    }

    public void addFavRepo(Repository repo){
        
    	try {
            this.favRepos.add(repo);
        }
        
        catch (Exception e) {
            return;
        }
        
    }
}
