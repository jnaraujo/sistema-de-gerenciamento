package com.uefs.sistemadegerenciamento.dao.cleaning_service;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.CleaningService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CleaningServiceTest {
    private CleaningServiceDao cleaningServiceDao;
    private CleaningService cleaningService;

    @BeforeEach
    void setUp(){
        cleaningServiceDao = DAOManager.getCleaningServiceDao();
        cleaningService = new CleaningService(
                IdGenerator.generate(),
                70.0,
                35.5
        );
    }

    @AfterEach
    void tearDown(){
        cleaningServiceDao.deleteAll();
    }

    @Test
    void testSave() {
        cleaningServiceDao.save(cleaningService);
        assertEquals(cleaningService, cleaningServiceDao.findById(cleaningService.getId()));
    }

    @Test
    void testDelete(){
        cleaningServiceDao.save(cleaningService);
        assertEquals(cleaningService, cleaningServiceDao.findById(cleaningService.getId()));

        cleaningServiceDao.delete(cleaningService.getId());
        assertNull(cleaningServiceDao.findById(cleaningService.getId()));
    }

    @Test
    void testUpdate(){
        cleaningServiceDao.save(cleaningService);
        assertEquals(70.0, cleaningServiceDao.findById(cleaningService.getId()).getPrice());

        cleaningService.setPrice(150.0);
        cleaningServiceDao.update(cleaningService);

        assertEquals(150.0, cleaningServiceDao.findById(cleaningService.getId()).getPrice());
    }

    @Test
    void testGetAll(){
        cleaningServiceDao.save(cleaningService);

        CleaningService cleaningService2 = new CleaningService(
                IdGenerator.generate(),
                50.0,
                12.0
        );
        cleaningServiceDao.save(cleaningService2);

        List<CleaningService> allServices = cleaningServiceDao.getAll();

        assertEquals(2, allServices.size());
        assertTrue(allServices.contains(cleaningService));
        assertTrue(allServices.contains(cleaningService2));

    }
}