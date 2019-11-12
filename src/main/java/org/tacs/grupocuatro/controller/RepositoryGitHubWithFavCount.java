package org.tacs.grupocuatro.controller;

import org.tacs.grupocuatro.github.entity.RepositoryGitHub;

public class RepositoryGitHubWithFavCount extends RepositoryGitHub {
    private long favCount;

    public RepositoryGitHubWithFavCount(RepositoryGitHub old, long favCount) {
        super(old.getId(), old.getName(), old.getNumForks(), old.getNumStars(), old.getLanguage(), old.getTags());
        this.setFavCount(favCount);
    }

    public long getFavCount() {
        return favCount;
    }

    public void setFavCount(long favCount) {
        this.favCount = favCount;
    }
}
