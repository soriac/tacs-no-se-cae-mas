package org.tacs.grupocuatro.entity;

import org.tacs.grupocuatro.entity.Repository;

import java.util.List;
import java.util.Set;

public class UserCompareListData {
    private Set<Repository> sharedRepos;
    private Set<String> sharedLanguages;

    public UserCompareListData(Set<Repository> sharedRepos, Set<String> sharedLanguages) {
        this.sharedRepos = sharedRepos;
        this.sharedLanguages = sharedLanguages;
    }

    public Set<Repository> getSharedRepos() { return sharedRepos; }

    public Set<String> getSharedLanguages() {
        return sharedLanguages;
    }

}
