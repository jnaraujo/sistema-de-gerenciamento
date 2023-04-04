package com.uefs.sistemadegerenciamento.dao.user;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.Technician;
import com.uefs.sistemadegerenciamento.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserDaoTest {
    private UserDao userDao;
    private User user;

    @BeforeEach
    void setUp(){
        userDao = DAOManager.getUserDao();
        user = new Technician(
                UUID.randomUUID().toString(),
                "João da Silva",
                "joao@test.com"
        );
    }

    @AfterEach
    void tearDown() {
        userDao.deleteAll();
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
                "test@test.com"
        ));
        userDao.save(user);

        assertTrue(userDao.getAll().contains(user));
        assertTrue(userDao.getAll().contains(new Technician(
                "test",
                "test",
                "test@test.com"
        )));
    }
}