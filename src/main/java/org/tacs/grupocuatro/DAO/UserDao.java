package org.tacs.grupocuatro.DAO;

import org.tacs.grupocuatro.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {

    private List<User> users = new ArrayList<>();

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get((int) id));
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        users.removeIf(x -> x.getId() == user.getId());
        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }
}