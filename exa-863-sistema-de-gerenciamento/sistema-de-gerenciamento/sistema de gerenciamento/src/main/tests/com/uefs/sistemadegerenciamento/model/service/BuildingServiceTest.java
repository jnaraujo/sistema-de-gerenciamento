package com.uefs.sistemadegerenciamento.model.service;

import com.uefs.sistemadegerenciamento.model.component.ComputerComponent;
import com.uefs.sistemadegerenciamento.model.service.BuildingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingServiceTest {

    @Test
    void testBuildingService() {
        BuildingService buildingService = new BuildingService();

        buildingService.addComponent(new ComputerComponent(
                "1",
                "HYPERX 8GB 2400MHz",
                "HYPERX",
                200.0,
                100.0,
                1
        ));
        buildingService.addComponent(new ComputerComponent(
                "2",
                "HYPERX 8GB 2400MHz",
                "HYPERX",
                200.0,
                125.5,
                1
        ));

        assertEquals(400.0, buildingService.getPrice());
        assertEquals(225.5, buildingService.getCost());
        assertEquals(2, buildingService.getUsedComponents().size());
    }

}