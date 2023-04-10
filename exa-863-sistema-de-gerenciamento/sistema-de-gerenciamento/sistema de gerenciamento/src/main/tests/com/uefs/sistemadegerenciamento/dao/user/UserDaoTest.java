package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.Administrator;
import com.uefs.sistemadegerenciamento.model.user.Receptionist;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import com.uefs.sistemadegerenciamento.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao userDao;
    private User user;

    @BeforeEach
    void setUp(){
        userDao = DAOManager.getUserDao();
        user = new Technician(
                UUID.randomUUID().toString(),
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
        userDao.save(user);
        assertEquals(user, userDao.findById(user.getId()));
        assertEquals(Technician.class, userDao.findById(user.getId()).getClass());
    }

    @Test
    void testSave() {
        userDao.save(user);
        assertEquals(user, userDao.findById(user.getId()));
    }

    @Test
    void testDelete(){
        userDao.save(user);
        assertNotNull(userDao.findById(user.getId()));
        userDao.delete(user.getId());
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    void testUpdate(){
        userDao.save(user);
        assertEquals("João da Silva", userDao.findById(user.getId()).getName());

        user.setName("José Marcio");

        userDao.update(user);
        assertEquals("José Marcio", userDao.findById(user.getId()).getName());
    }

    @Test
    void testGetAll(){
        userDao.save(new Technician(
                "test",
                "test",
                "test@test.com",
                "123456"
        ));
        userDao.save(user);

        assertTrue(userDao.getAll().contains(user));
        assertTrue(userDao.getAll().contains(new Technician(
                "test",
                "test",
                "test@test.com",
                "123456"
        )));
    }

    @Test
    void testFindAllTechnicians(){
        userDao.save(new Technician(
                "test",
                "test",
                "",
                ""
        ));

        userDao.save(user);

        assertTrue(userDao.findAllTechnicians().contains(user));
    }

    @Test
    void testFindAllAdministrators(){
        User admin = new Administrator(
                "adminid",
                "test",
                "",
                ""
        );
        userDao.save(admin);

        userDao.save(user);

        assertFalse(userDao.findAllAdministrators().contains(user));
        assertTrue(userDao.findAllAdministrators().contains(admin));
    }

    @Test
    void testFindAllReceptionists(){
        User receptionist = new Receptionist(
                "receptionistid",
                "test",
                "",
                ""
        );
        userDao.save(receptionist);

        userDao.save(user);

        assertFalse(userDao.findAllReceptionists().contains(user));
        assertTrue(userDao.findAllReceptionists().contains(receptionist));
    }
}