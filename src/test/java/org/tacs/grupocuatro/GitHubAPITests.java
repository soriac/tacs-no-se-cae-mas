package org.tacs.grupocuatro;

import org.junit.jupiter.api.Test;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.entity.RepositoriesGitHub;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.enums.*;
import org.tacs.grupocuatro.github.exceptions.GitHubConnectionException;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;
import org.tacs.grupocuatro.github.query.GitHubQueryDecorator;
import org.tacs.grupocuatro.github.query.decorators.Between;
import org.tacs.grupocuatro.github.query.decorators.Comparison;
import org.tacs.grupocuatro.github.query.decorators.Operation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GitHubAPITests {

    @Test
    void connectionToAPI() throws GitHubConnectionException {
        GitHubConnect conn = GitHubConnect.getInstance();
        conn.tryConnection();
    }

    @Test
    void usingToken() {
        GitHubConnect conn = GitHubConnect.getInstance();
        assertEquals(30, conn.getLimits().get("limit"));
    }

    @Test
    void getRepoWithDecoratorsWithoutKeywords() throws GitHubRequestLimitExceededException {
        GitHubConnect conn = GitHubConnect.getInstance();

        List<GitHubQueryDecorator> deco = new ArrayList<GitHubQueryDecorator>();
        deco.add(new Comparison(ValueType.STARS, Comparator.GREATER, 5));
        deco.add(new Operation(Operator.AND));
        deco.add(new Between(ValueType.FORKS, 10, 20));

        RepositoriesGitHub repo = conn.searchRepository(Order.ASC, Sort.STARS, deco);

        assertTrue(repo.getRepos().size() > 0);
    }


    @Test
    void getRepoWithOutDecoratorsWithKeywords() throws GitHubRequestLimitExceededException {
        GitHubConnect conn = GitHubConnect.getInstance();

        List<GitHubQueryDecorator> deco = new ArrayList<>();

        RepositoriesGitHub repo = conn.searchRepository(Order.ASC, Sort.STARS, deco, "jquery", "test");

        assertTrue(repo.getRepos().size() > 0);
    }

    @Test
    void getRepoWithDecoratorsWithKeywords() throws GitHubRequestLimitExceededException {
        GitHubConnect conn = GitHubConnect.getInstance();

        List<GitHubQueryDecorator> deco = new ArrayList<>();
        deco.add(new Comparison(ValueType.STARS, Comparator.GREATER, 5));
        deco.add(new Operation(Operator.AND));
        deco.add(new Between(ValueType.FORKS, 10, 20));

        RepositoriesGitHub repo = conn.searchRepository(Order.ASC, Sort.STARS, deco, "jquery", "test");

        assertTrue(repo.getRepos().size() > 0);
    }

    @Test
    void getRepoById() throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
        GitHubConnect conn = GitHubConnect.getInstance();
        RepositoryGitHub repo = conn.findRepositoryById(20633049);

        assertNotNull(repo);
    }

    @Test
    void getRepoByIdException() {
        GitHubConnect conn = GitHubConnect.getInstance();

        assertThrows(GitHubRepositoryNotFoundException.class, () -> conn.findRepositoryById(981938123));
    }

    @Test
    void getRepoCommits() {
        GitHubConnect conn = GitHubConnect.getInstance();
        var repos = conn.getRepositoryWithCommits("soriac", "tacs-no-se-cae-mas");

        assertTrue(repos.size() >= 1);
        assertTrue(repos.size() <= 10);
    }

    @Test
    void getRepoCommitsThrowsOnInvalidAuthor() {
        GitHubConnect conn = GitHubConnect.getInstance();
        assertThrows(
                RuntimeException.class,
                () -> conn.getRepositoryWithCommits(
                        "soriac-this-is-an-invalid-name-noone-should-have-this-name",
                        "tacs-no-se-cae-mas"
                )
        );
    }

    @Test
    void getRepoCommitsThrowsOnInvalidName() {
        GitHubConnect conn = GitHubConnect.getInstance();
        assertThrows(
                RuntimeException.class,
                () -> conn.getRepositoryWithCommits(
                        "soriac",
                        "$%^$%&#$%^ si llega a existir alguna vez este repo está bien, puede fallar el test :)"
                )
        );
    }
}
