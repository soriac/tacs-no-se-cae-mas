package org.tacs.grupocuatro.DAO;

import org.tacs.grupocuatro.github.exceptions.GitHubRepositoryNotFoundException;
import org.tacs.grupocuatro.github.exceptions.GitHubRequestLimitExceededException;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(String id) throws GitHubRepositoryNotFoundException, GitHubRequestLimitExceededException;

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}