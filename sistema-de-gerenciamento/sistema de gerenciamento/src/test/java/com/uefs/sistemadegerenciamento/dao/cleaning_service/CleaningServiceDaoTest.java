package com.uefs.sistemadegerenciamento.dao.cleaning_service;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CleaningServiceDaoTest {
    private CleaningServiceDao cleaningServiceDao;
    private CleaningService cleaningService;

    @BeforeEach
    void setUp(){
        cleaningServiceDao = DAOManager.getCleaningServiceDao();
        cleaningService = new CleaningService(
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
        cleaningService = cleaningServiceDao.save(cleaningService);
        assertEquals(cleaningService, cleaningServiceDao.findById(cleaningService.getId()));
    }

    @Test
    void testSaveWithSameId(){
        cleaningService = cleaningServiceDao.save(cleaningService);
        CleaningService cleaningService2 = new CleaningService(
                50.0,
                12.0
        );
        cleaningService2.setId(cleaningService.getId());
        cleaningServiceDao.save(cleaningService2);

        assertEquals(2, cleaningServiceDao.getAll().size());
        assertEquals(cleaningService, cleaningServiceDao.findById(cleaningService.getId()));
        assertEquals(cleaningService2, cleaningServiceDao.findById(cleaningService2.getId()));
    }

    @Test
    void testDelete(){
        cleaningService = cleaningServiceDao.save(cleaningService);
        assertEquals(cleaningService, cleaningServiceDao.findById(cleaningService.getId()));

        cleaningServiceDao.delete(cleaningService.getId());
        assertNull(cleaningServiceDao.findById(cleaningService.getId()));
    }

    @Test
    void testUpdate(){
        cleaningService = cleaningServiceDao.save(cleaningService);
        assertEquals(70.0, cleaningServiceDao.findById(cleaningService.getId()).getPrice());

        cleaningService.setPrice(150.0);
        cleaningServiceDao.update(cleaningService);

        assertEquals(150.0, cleaningServiceDao.findById(cleaningService.getId()).getPrice());
    }

    @Test
    void testGetAll(){
        cleaningService = cleaningServiceDao.save(cleaningService);

        CleaningService cleaningService2 = new CleaningService(
                50.0,
                12.0
        );
        cleaningService2 = cleaningServiceDao.save(cleaningService2);

        List<CleaningService> allServices = cleaningServiceDao.getAll();

        assertEquals(2, allServices.size());
        assertTrue(allServices.contains(cleaningService));
        assertTrue(allServices.contains(cleaningService2));

    }
}