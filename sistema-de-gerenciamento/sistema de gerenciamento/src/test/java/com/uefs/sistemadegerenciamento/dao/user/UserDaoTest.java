package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao userDao;
    private User loggedUser;

    @BeforeEach
    void setUp(){
        userDao = DAOManager.getUserDao();
        loggedUser = new Technician(
                "João da Silva",
                "joao@test.com",
                "123456"
        );
    }

    @AfterEach
    void tearDown() {
        userDao.deleteAll();
    }

    @Test
    void testFindById(){
        loggedUser = userDao.save(loggedUser);
        assertEquals(loggedUser, userDao.findById(loggedUser.getId()));
        assertEquals(Technician.class, userDao.findById(loggedUser.getId()).getClass());
    }

    @Test
    void testSave() {
        loggedUser = userDao.save(loggedUser);
        assertEquals(loggedUser, userDao.findById(loggedUser.getId()));
    }

    @Test
    void testDelete(){
        loggedUser = userDao.save(loggedUser);

        assertNotNull(userDao.findById(loggedUser.getId()));
        userDao.delete(loggedUser.getId());
        assertNull(userDao.findById(loggedUser.getId()));
    }

    @Test
    void testUpdate(){
        loggedUser = userDao.save(loggedUser);
        assertEquals("João da Silva", userDao.findById(loggedUser.getId()).getName());

        loggedUser.setName("José Marcio");

        userDao.update(loggedUser);
        assertEquals("José Marcio", userDao.findById(loggedUser.getId()).getName());
    }

    @Test
    void testGetAll(){
        User technician = new Technician(
                "test",
                "test@test.com",
                "123456"
        );
        technician.setId(IdGenerator.generate());

        technician = userDao.save(technician);
        loggedUser = userDao.save(loggedUser);

        assertTrue(userDao.getAll().contains(loggedUser));

        User expected = new Technician(
                "test",
                "test@test.com",
                "123456"
        );
        expected.setId(technician.getId());
        assertTrue(userDao.getAll().contains(expected));
    }

    @Test
    void testFindAllTechnicians(){
        userDao.save(new Technician(
                "test",
                "",
                ""
        ));

        loggedUser = userDao.save(loggedUser);

        assertTrue(userDao.findAllTechnicians().contains((Technician) loggedUser));
    }

    @Test
    void testFindAllAdministrators(){
        Administrator admin = new Administrator(
                "test",
                "",
                ""
        );

        userDao.save(admin);

        assertTrue(userDao.findAllAdministrators().contains(admin));
    }

    @Test
    void testFindAllReceptionists(){
        Receptionist receptionist = new Receptionist(
                "test",
                "test@test.com",
                "teste"
        );
        userDao.save(receptionist);

        assertTrue(userDao.findAllReceptionists().contains(receptionist));
    }

    @Test
    void testFindByEmail(){
        loggedUser = userDao.save(loggedUser);
        assertEquals(loggedUser, userDao.findByEmail(loggedUser.getEmail()));
    }
}