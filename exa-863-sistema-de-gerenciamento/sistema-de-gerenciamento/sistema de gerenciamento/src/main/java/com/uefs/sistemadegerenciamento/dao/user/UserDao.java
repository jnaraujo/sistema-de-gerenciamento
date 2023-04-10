package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.dao.Dao;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    List<Technician> findAllTechnicians();
    List<Administrator> findAllAdministrators();
    List<Receptionist> findAllReceptionists();
}
