package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryUserDao implements UserDao{
    private HashMap<String, User> users = new HashMap<>();
    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public void delete(String userID) {
        users.remove(userID);
    }

    @Override
    public void update(User user) {
        users.replace(user.getId(), user);
    }

    @Override
    public User findById(String id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteAll() {
        users.clear();
    }
}
