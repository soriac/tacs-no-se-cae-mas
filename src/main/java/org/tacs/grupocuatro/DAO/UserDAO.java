package org.tacs.grupocuatro.DAO;

import org.tacs.grupocuatro.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User> {

    private static UserDAO instance;
    private static long id = 1;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }

        return instance;
    }

    private List<User> users = new ArrayList<>();

    @Override
    public Optional<User> get(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        user.setId("" + id);
        id++;
        users.add(user);
    }

    @Override
    public void update(User user) {
        users.removeIf(x -> x.getId().equals(user.getId()));
        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

    public Optional<User> findByUser(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }
}