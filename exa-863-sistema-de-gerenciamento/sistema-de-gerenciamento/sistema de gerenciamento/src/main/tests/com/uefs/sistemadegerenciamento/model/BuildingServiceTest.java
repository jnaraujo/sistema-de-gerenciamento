package com.uefs.sistemadegerenciamento.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingServiceTest {

    @Test
    void testBuildingService() {
        BuildingService buildingService = new BuildingService();

        buildingService.addComponent(new ComputerComponent(
                "HYPERX 8GB 2400MHz",
                "HYPERX",
                200.0,
                100.0
        ));
        buildingService.addComponent(new ComputerComponent(
                "HYPERX 8GB 2400MHz",
                "HYPERX",
                200.0,
                125.5
        ));

        assertEquals(400.0, buildingService.getPrice());
        assertEquals(225.5, buildingService.getCost());
        assertEquals(2, buildingService.getUsedComponents().size());
    }

}