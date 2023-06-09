package com.uefs.sistemadegerenciamento.model.service;

import com.uefs.sistemadegerenciamento.model.service.CleaningService;
import com.uefs.sistemadegerenciamento.utils.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleaningServiceTest {
    @Test
    void testCleaning() {
        double price = 50.0;
        double cost = 25.0;

        CleaningService cleaningService = new CleaningService(
                price,
                cost
        );
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
    void testId(){
        String id = IdGenerator.generate();

        CleaningService service = new CleaningService(50, 75);
        service.setId(id);

        assertEquals(id, service.getId());

        String newId = IdGenerator.generate();
        service.setId(newId);

        assertEquals(newId, service.getId());
    }

    @Test
    void testEquals(){
        String id = IdGenerator.generate();

        CleaningService service1 = new CleaningService(50, 75);
        service1.setId(id);

        CleaningService service2 = new CleaningService(25, 36);

        assertFalse(service1.equals(service2));

        service2.setId(id);

        assertTrue(service1.equals(service2));
    }

    @Test
    void testToString(){
        String id = IdGenerator.generate();

        CleaningService service = new CleaningService(50, 75);
        service.setId(id);

        assertEquals("CleaningService [id=" + id + ", price=50.0, cost=75.0]", service.toString());
    }
}