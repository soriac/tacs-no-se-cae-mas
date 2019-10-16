package org.tacs.grupocuatro.entity;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class User {

    private String id;
    private ApplicationRole role;

    private String email;
    private String password;

    private Date lastLogin;
    private Set<Repository> favoriteRepos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFavoriteLanguage() {
        if (favoriteRepos.size() == 0) {
            return "";
        }

        Map<String, Long> languageCountList = favoriteRepos.stream().collect(
                Collectors.groupingBy(Repository::getLanguage, Collectors.counting()));

        Optional<Map.Entry<String, Long>> maxEntry = languageCountList.entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue)
                );

        return maxEntry.get().getKey();
    }
}
