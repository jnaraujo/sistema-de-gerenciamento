package com.uefs.sistemadegerenciamento.model.service;

import com.uefs.sistemadegerenciamento.model.service.InstallationService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstallationServiceTest {
    @Test
    void testInstallation() {
        double price = 50.0;
        double cost = 25.0;

        InstallationService installationService = new InstallationService(
                IdGenerator.generate(),
                "Instalação de Windows 10",
                price,
                cost
        );

        assertEquals(cost, installationService.getCost());
        assertEquals(price, installationService.getPrice());
    }

    @Test
    void testToString() {
        InstallationService installationService = new InstallationService(
                IdGenerator.generate(),
                "Instalação de Windows 10",
                50.0,
                25.0
        );

        String expected = "InstallationService [id=" + installationService.getId() + ", cost=25.0, price=50.0, " +
                "description=Instalação de Windows 10]";
        assertEquals(expected, installationService.toString());
    }

}