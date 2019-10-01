package org.tacs.grupocuatro.DAO;

import org.tacs.grupocuatro.entity.Repository;
import org.tacs.grupocuatro.github.GitHubConnect;
import org.tacs.grupocuatro.github.entity.RepositoryGitHub;
import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class RepositoryDAO implements DAO<Repository> {

    private static RepositoryDAO instance;

    public static RepositoryDAO getInstance() {
        if (instance == null) {
            instance = new RepositoryDAO();
        }

        return instance;
    }

    private List<Repository> repositories = new ArrayList<>();

    @Override
    public Optional<Repository> get(String id) {
        return repositories.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Repository getOrAdd(String id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
        var repo = repositories.stream().filter(u -> u.getId().equals(id)).findFirst();

        if (repo.isEmpty()) {
            var foundRepo = GitHubConnect.getInstance().findRepositoryById(id);
            var localRepo = new Repository(foundRepo.getId() + "", foundRepo.getName());
            this.save(localRepo);
            return localRepo;
        }

        return repo.get();
    }

    public RepositoryGitHub fetchById(String id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException {
        return GitHubConnect.getInstance().findRepositoryById(id);
    }

    @Override
    public List<Repository> getAll() {
        return repositories;
    }

    @Override
    public void save(Repository repo) {
        repo.setAdded(new Date());
        repositories.add(repo);
    }

    @Override
    public void update(Repository repo) {
        repositories.removeIf(x -> x.getId().equals(repo.getId()));
        repositories.add(repo);
    }

    @Override
    public void delete(Repository repo) {
        repositories.remove(repo);
    }

    public long favCountForId(String id) {
        var repo = repositories.stream().filter(r -> r.getId().equals(id)).findFirst();

        if (repo.isEmpty()) return 0;

        return UserDAO.getInstance()
                .getAll()
                .stream()
                .filter(u -> u.getFavRepos().contains(repo.get()))
                .count();
    }
}
