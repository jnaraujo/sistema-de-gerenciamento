package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstallationServiceTest {
    @Test
    void testInstallation() {
        double price = 50.0;
        double cost = 25.0;

        InstallationService installationService = new InstallationService(IdGenerator.generate(), price, cost);
        installationService.setOperatingSystem("Windows 10");
        installationService.addProgram("Office");
        installationService.addProgram("Photoshop");

        assertEquals(cost, installationService.getCost());
        assertEquals(price, installationService.getPrice());
        assertEquals("Windows 10", installationService.getOperatingSystem());
        assertEquals(2, installationService.getPrograms().size());
        assertEquals("Office", installationService.getPrograms().get(0));
        assertEquals("Photoshop", installationService.getPrograms().get(1));
    }

}