package org.tacs.grupocuatro.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(String id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}