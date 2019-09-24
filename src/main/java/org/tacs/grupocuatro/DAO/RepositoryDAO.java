package org.tacs.grupocuatro.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.tacs.grupocuatro.entity.Repository;

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
        var repo = repositories.stream().filter(u -> u.getId().equals(id)).findFirst();
        if (repo.isEmpty()) {
            // pedirlo al api de GH
        }

        return repo;
    }

    @Override
    public List<Repository> getAll() {
        return repositories;
    }

    @Override
    public void save(Repository repo) {
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
}
