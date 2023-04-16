package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.constants.UserType;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryUserDao implements UserDao{
    private HashMap<String, User> users = new HashMap<>();
    @Override
    public User save(User user) {
        String id = IdGenerator.generate();
        user.setId(id);

        users.put(user.getId(), user);

        return user;
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

    @Override
    public List<Technician> findAllTechnicians() {
        List<Technician> technicians = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getUserType().equals(UserType.TECHNICIAN)) {
                technicians.add((Technician) user);
            }
        }
        return technicians;
    }

    @Override
    public List<Administrator> findAllAdministrators() {
        List<Administrator> administrators = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getUserType().equals(UserType.ADMINISTRATOR)) {
                administrators.add((Administrator) user);
            }
        }
        return administrators;
    }

    @Override
    public List<Receptionist> findAllReceptionists() {
        List<Receptionist> receptionists = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getUserType().equals(UserType.RECEPTIONIST)) {
                receptionists.add((Receptionist) user);
            }
        }
        return receptionists;
    }
}
