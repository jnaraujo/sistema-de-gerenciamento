package com.uefs.sistemadegerenciamento.dao.installation_service;

import com.uefs.sistemadegerenciamento.dao.DAOManager;
import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstallationServiceDaoTest {
    private InstallationServiceDao dao;
    private InstallationService installationService;

    @BeforeEach
    void setUp() {
        dao = DAOManager.getInstallationServiceDao();
        installationService = new InstallationService(
                IdGenerator.generate(),
                70.0,
                35.5
        );
    }

    @AfterEach
    void tearDown() {
        dao.deleteAll();
    }

    @Test
    void testSave() {
        dao.save(installationService);
        assertEquals(installationService, dao.findById(installationService.getId()));
    }

    @Test
    void testDelete(){
        dao.save(installationService);
        assertEquals(installationService, dao.findById(installationService.getId()));

        dao.delete(installationService.getId());
        assertNull(dao.findById(installationService.getId()));
    }

    @Test
    void testUpdate(){
        dao.save(installationService);
        assertEquals(70.0, dao.findById(installationService.getId()).getPrice());

        installationService.setPrice(150.0);
        dao.update(installationService);

        assertEquals(150.0, dao.findById(installationService.getId()).getPrice());
    }

    @Test
    void testGetAll(){
        dao.save(installationService);

        InstallationService installationService2 = new InstallationService(
                IdGenerator.generate(),
                50.0,
                12.0
        );
        dao.save(installationService2);

        List<InstallationService> allServices = dao.getAll();

        assertEquals(2, allServices.size());
        assertTrue(allServices.contains(installationService));
        assertTrue(allServices.contains(installationService2));
    }
}