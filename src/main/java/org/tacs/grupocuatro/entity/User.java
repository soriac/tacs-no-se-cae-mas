package org.tacs.grupocuatro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.tacs.grupocuatro.DAO.UserDAO;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private ApplicationRole role;

    private String email;
    private String password;

    private Date lastLogin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "favorite_repos",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "repository_id") }
    )
    @JsonManagedReference
    private Set<Repository> favoriteRepos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return this.favoriteRepos;
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
        UserDAO.getInstance().update(this);
    }

    public void removeFavoriteRepo(Repository repository) {
        favoriteRepos.removeIf(repo -> repo.getId() == repository.getId());
        UserDAO.getInstance().update(this);
    }

    public String getFavoriteLanguage() {
        if (favoriteRepos == null || favoriteRepos.size() == 0) {
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
