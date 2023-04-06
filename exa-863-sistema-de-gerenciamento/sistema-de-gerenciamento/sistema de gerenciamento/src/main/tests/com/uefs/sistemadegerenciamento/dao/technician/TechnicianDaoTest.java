package com.uefs.sistemadegerenciamento.dao.technician;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.user.Technician;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TechnicianDaoTest {
    private TechnicianDao technicianDao;
    private Technician technician;

    @BeforeEach
    void setUp() {
        technicianDao = DAOManager.getTechnicianDao();
        technician = new Technician("1", "João da Silva", "joao@test.com", "123456");
    }

    @AfterEach
    void tearDown() {
        technicianDao.deleteAll();
    }

    @Test
    void testSave() {
        technicianDao.save(technician);
        assertEquals(technician, technicianDao.findById(technician.getId()));
    }

    @Test
    void testDelete() {
        technicianDao.save(technician);
        assertNotNull(technicianDao.findById(technician.getId()));
        technicianDao.delete(technician.getId());
        assertNull(technicianDao.findById(technician.getId()));
    }

    @Test
    void testUpdate() {
        technicianDao.save(technician);
        assertEquals("João da Silva", technicianDao.findById(technician.getId()).getName());
        technician.setName("José da Silva");
        technicianDao.update(technician);
        assertEquals("José da Silva", technicianDao.findById(technician.getId()).getName());
    }

    @Test
    void testFindById() {
        technicianDao.save(technician);
        assertEquals(technician, technicianDao.findById(technician.getId()));
    }

    @Test
    void testGetAll(){
        technicianDao.save(new Technician(
                "test",
                "test",
                "test@test.com",
                "123456"
        ));
        technicianDao.save(technician);

        assertTrue(technicianDao.getAll().contains(technician));
        assertTrue(technicianDao.getAll().contains(new Technician(
                "test",
                "test with another name",
                "tdasdasdom",
                "123456"
        )));
    }
}