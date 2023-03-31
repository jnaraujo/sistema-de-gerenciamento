package com.uefs.sistemadegerenciamento.dao.technician;

import com.uefs.sistemadegerenciamento.model.Technician;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTechnicianDaoTest {
    private InMemoryTechnicianDao technicianDao;
    private Technician technician;

    @BeforeEach
    void setUp() {
        technicianDao = new InMemoryTechnicianDao();
        technician = new Technician("1", "João da Silva", "joao@test.com");
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
                "test@test.com"
        ));
        technicianDao.save(technician);

        assertTrue(technicianDao.getAll().contains(technician));
        assertTrue(technicianDao.getAll().contains(new Technician(
                "test",
                "test with another name",
                "tdasdasdom"
        )));
    }
}