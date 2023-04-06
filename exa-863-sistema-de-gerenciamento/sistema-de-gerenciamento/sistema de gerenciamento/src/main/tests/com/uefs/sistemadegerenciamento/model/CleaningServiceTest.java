package com.uefs.sistemadegerenciamento.model;

import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleaningServiceTest {
    @Test
    void testCleaning() {
        double price = 50.0;
        double cost = 25.0;

        CleaningService cleaningService = new CleaningService(IdGenerator.generate(), price, cost);
        cleaningService.addComponent("HD");
        cleaningService.addComponent("Placa Mãe");
        cleaningService.addComponent("Placa de Vídeo");

        assertEquals(cost, cleaningService.getCost());
        assertEquals(price, cleaningService.getPrice());
        assertEquals(3, cleaningService.getComponents().size());
        assertEquals("HD", cleaningService.getComponents().get(0));
        assertEquals("Placa Mãe", cleaningService.getComponents().get(1));
        assertEquals("Placa de Vídeo", cleaningService.getComponents().get(2));
    }

    @Test
    void testEquals(){
        String id = IdGenerator.generate();

        CleaningService service1 = new CleaningService(id, 50, 75);
        CleaningService service2 = new CleaningService("anotherid", 25, 36);

        assertFalse(service1.equals(service2));

        service2.setId(id);

        assertTrue(service1.equals(service2));
    }
}