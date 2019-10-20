package org.tacs.grupocuatro.controller;

import java.util.List;

public class Data {
    private List<String> sharedRepos;
    private List<String> sharedLanguages;

    public Data(List<String> sharedRepos, List<String> sharedLanguages) {
        this.sharedRepos = sharedRepos;
        this.sharedLanguages = sharedLanguages;
    }

    public List<String> getSharedRepos() { return sharedRepos; }

    public List<String> getSharedLanguages() {
        return sharedLanguages;
    }

}
